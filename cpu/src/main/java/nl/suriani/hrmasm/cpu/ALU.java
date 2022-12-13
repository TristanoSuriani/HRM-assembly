package nl.suriani.hrmasm.cpu;

public class ALU {
    private Value a;
    private Value b;

    public ALU() {
        reset();
    }

    public void storeA(Value a) {
        this.a = a;
    }

    public void storeB(Value a) {
        this.a = a;
    }

    public Value add() {
        return a.plus(b);
    }

    public Value subtract() {
        return a.minus(b);
    }

    private void reset() {
        a = new Value();
        b = new Value();
    }
}
