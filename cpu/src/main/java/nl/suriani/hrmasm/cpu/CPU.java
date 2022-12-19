package nl.suriani.hrmasm.cpu;

import nl.suriani.hrmasm.program.Program;

import java.util.List;
import java.util.Optional;

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
    private int instructionsCounter;

    private static final String INSTRUCTION_DEBUG_TEMPLATE = "(%s) %s - %s %s";

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
        while (programCounter.instructionNumber() < program.instructions().size() &&
            !List.of(CpuStatus.HALTED, CpuStatus.PAUSED).contains(status)) {

            printDebugInstructionInfo();

            if (instructionsCounter == MAXMUM_AMOUNT_INSTRUCTION_PER_PROGRAM_EXECUTION) {
                status = CpuStatus.HALTED_ABNORMALLY;
                return this;
            }

            instructionsCounter += 1;

            var instruction = program.instructions().get(programCounter.instructionNumber());
            var maybeParam1 = instruction.params().stream().findFirst();

            switch (instruction.type()) {
                case ADD -> handleAdd(maybeParam1.get());
                case COPY_FROM -> handleCopyFrom(maybeParam1.get());
                case COPY_TO -> handleCopyTo(maybeParam1.get());
                case INBOX -> handleInbox();
                case OUTBOX -> handleOutbox();
                case JUMP -> {
                    handleJump(instruction.params().get(0));
                    continue;
                }
            }
            programCounter.increment();

        }
        status = CpuStatus.HALTED;
        return this;
    }

    public CPU step() {
        //TODO
        return this;
    }

    public boolean isReady() {
        return CpuStatus.READY == status;
    }

    public boolean isBusy() {
        return CpuStatus.BUSY == status;
    }

    public boolean isPaused() {
        return CpuStatus.PAUSED == status;
    }

    public boolean isHalted() {
        return CpuStatus.HALTED == status;
    }

    public boolean isHaltedAbnormally() {
        return CpuStatus.HALTED_ABNORMALLY == status;
    }

    void addToInbox(Value value) {
        inbox.add(value);
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

    void storeIntoRegister(int idx, Value value) {
        registers.store(idx, value);
    }

    Debugger getDebugger() {
        return new Debugger(inbox.debug(), outbox.debug());
    }

    CPU debug() {
        debug = true;
        return this;
    }

    private void handleAdd(String param) {
        var value = registers.fetch(Integer.parseInt(param));
        var a = mRegister.fetch();
        var result = performAddition(a, value);
        mRegister.store(result);
        registers.store(Integer.parseInt(param), result);
    }

    private Value performAddition(Value a, Value b) {
        alu.storeA(a);
        alu.storeB(b);
        var result = alu.add();
        return result;
    }

    private void handleSubtract(String param) {
        var value = registers.fetch(Integer.parseInt(param));
        var a = mRegister.fetch();
        var result = performSubtraction(a, value);
        mRegister.store(result);
        registers.store(Integer.parseInt(param), result);
    }

    private Value performSubtraction(Value a, Value b) {
        alu.storeA(a);
        alu.storeB(b);
        var result = alu.subtract();
        return result;
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

    private void handleCopyFrom(String param) {
        var value = registers.fetch(Integer.parseInt(param));
        mRegister.store(value);
    }

    private void handleCopyTo(String param) {
        var value = new Value(Integer.parseInt(param));
        registers.store(value.asNumber(), mRegister.fetch());
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
        debug = false;
        instructionsCounter = 0;
    }

    private void printDebugInstructionInfo() {
        if (debug) {
            var debugInfo = String.format(INSTRUCTION_DEBUG_TEMPLATE,
                    instructionsCounter,
                    programCounter.instructionNumber(),
                    program.instructions().get(programCounter.instructionNumber()).type(),
                    String.join(" ",
                            program.instructions().get(programCounter.instructionNumber()).params()));
            System.out.println(debugInfo);
        }
    }
}
