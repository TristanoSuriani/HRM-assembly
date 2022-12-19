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

    public void storeB(Value b) {
        this.b = b;
    }

    public Value add() {
        var result = a.plus(b);
        reset();
        return result;
    }

    public Value subtract() {
        var result = a.minus(b);
        reset();
        return result;
    }

    private void reset() {
        a = new Value();
        b = new Value();
    }
}
