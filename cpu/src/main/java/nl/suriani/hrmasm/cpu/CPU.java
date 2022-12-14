package nl.suriani.hrmasm.cpu;

import nl.suriani.hrmasm.program.Program;
import nl.suriani.hrmasm.program.Instruction;

import java.util.Optional;
import java.util.stream.Collectors;

public class CPU {
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
        while (programCounter.instructionNumber() < program.instructions().size()) {
            printDebugInstructionInfo();

            var instruction = program.instructions().get(programCounter.instructionNumber());

            switch (instruction.type()) {
                case INBOX -> handleInbox();
                case OUTBOX -> handleOutbox();
            }
            programCounter.increment();
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
        var value = popFromInbox().orElseThrow();
        mRegister.store(value);
    }

    private void handleOutbox() {
        var value = mRegister.fetch();
        pushIntoOutbox(value);
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
