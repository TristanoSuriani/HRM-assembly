package nl.suriani.hrmasm.cpu;

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
        //TODO
        return this;
    }

    public CPU step() {
        //TODO
        return this;
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
        program = new Program();
    }
}
