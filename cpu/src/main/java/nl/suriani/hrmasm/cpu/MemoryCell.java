package nl.suriani.hrmasm.cpu;

public class MemoryCell {
    private Value value;

    public MemoryCell() {
        this.value = new Value();
    }

    public Value fetch() {
        return value;
    }

    public void store(Value value) {
        this.value = value;
    }
}
