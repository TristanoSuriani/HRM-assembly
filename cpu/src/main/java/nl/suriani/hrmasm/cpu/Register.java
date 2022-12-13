package nl.suriani.hrmasm.cpu;

public class Register {
    private Value value;

    public Register() {
        this.value = new Value();
    }

    public Value fetch() {
        return value;
    }

    public void store(Value value) {
        this.value = value;
    }
}
