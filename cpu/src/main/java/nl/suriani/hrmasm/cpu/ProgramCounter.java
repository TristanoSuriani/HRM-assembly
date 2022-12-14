package nl.suriani.hrmasm.cpu;

public class ProgramCounter {
    private Value value;

    public ProgramCounter() {
        this.value = new Value();
    }

    public void store(Value value) {
        if (value.asNumber() < 0) {
            throw new IllegalStateException("The program counter can only have positive values");
        }
        this.value = value;
    }

    public void increment() {
        value = value.increment();
    }

    public int instructionNumber() {
        return value.asNumber();
    }
}
