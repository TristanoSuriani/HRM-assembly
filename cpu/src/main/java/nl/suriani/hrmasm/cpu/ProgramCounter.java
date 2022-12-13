package nl.suriani.hrmasm.cpu;

public class ProgramCounter extends Register {
    @Override
    public void store(Value value) {
        if (value.asNumber() < 0) {
            throw new IllegalStateException("The program counter can only have positive values");
        }
        super.store(value);
    }
}
