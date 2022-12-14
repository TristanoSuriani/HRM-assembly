package nl.suriani.hrmasm.cpu;

import nl.suriani.hrmasm.program.Program;
import nl.suriani.hrmasm.program.Instruction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CPU {
    public static final int MAXMUM_AMOUNT_INSTRUCTION_PER_PROGRAM_EXECUTION = 16000;
    private CpuStatus status;
    private Registers registers;
    private Register mRegister;
    private ProgramCounter programCounter;
    private Inbox inbox;
    private Outbox outbox;
    private Stacks stacks;
    private ALU alu;
    private Program program;
    private Debugger debugger;
    private boolean debug;

    private static final String INSTRUCTION_DEBUG_TEMPLATE = "%s - %s %s";

    public CPU() {
        _reset();
    }

    public CPU reset() {
        _reset();
        return this;
    }

    public CPU load(Program program) {
        this.program = program;
        return this;
    }

    public CPU execute() {
        int instructionsCounter = 0;
        while (programCounter.instructionNumber() < program.instructions().size() &&
            !List.of(CpuStatus.HALTED, CpuStatus.PAUSED).contains(status)) {
            printDebugInstructionInfo();

            var instruction = program.instructions().get(programCounter.instructionNumber());

            switch (instruction.type()) {
                case INBOX -> handleInbox();
                case OUTBOX -> handleOutbox();
                case JUMP -> {
                    handleJump(instruction.params().get(0));
                    continue;
                }
            }
            programCounter.increment();
            instructionsCounter += 1;
            if (instructionsCounter == MAXMUM_AMOUNT_INSTRUCTION_PER_PROGRAM_EXECUTION) {
                status = CpuStatus.HALTED;
            }
        }
        return this;
    }

    public CPU step() {
        //TODO
        return this;
    }

    void pushIntoInbox(Value value) {
        inbox.push(value);
    }

    Optional<Value> popFromInbox() {
        return inbox.pop();
    }

    void pushIntoOutbox(Value value) {
        outbox.push(value);
    }

    Optional<Value> popFromOutbox(Value value) {
        return outbox.pop();
    }

    Debugger getDebugger() {
        return new Debugger(inbox.debug(), outbox.debug());
    }

    CPU debug() {
        debug = true;
        return this;
    }

    private void handleInbox() {
        var maybeValue = popFromInbox();
        if (maybeValue.isEmpty()) {
            status = CpuStatus.HALTED;
            return;
        }
        mRegister.store(maybeValue.get());
    }

    private void handleOutbox() {
        var value = mRegister.fetch();
        pushIntoOutbox(value);
    }

    private void handleJump(String param) {
        var value = new Value(Integer.parseInt(param));
        programCounter.store(value);
    }

    private void _reset() {
        status = CpuStatus.READY;
        registers = new Registers();
        mRegister = new Register();
        programCounter = new ProgramCounter();
        inbox = new Inbox();
        outbox = new Outbox();
        stacks = new Stacks();
        alu = new ALU();
        program = Program.program();
    }

    private void printDebugInstructionInfo() {
        if (debug) {
            var debugInfo = String.format(INSTRUCTION_DEBUG_TEMPLATE,
                    programCounter.instructionNumber(),
                    program.instructions().get(programCounter.instructionNumber()).type(),
                    String.join(" ",
                            program.instructions().get(programCounter.instructionNumber()).params()));
            System.out.println(debugInfo);
        }
    }
}
