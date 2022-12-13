package nl.suriani.hrmasm.cpu;

public record Value(int value) {
    public Value() {
        this(0);
    }

    public Value(char value) {
        this((int) value);
    }

    public Value increment() {
        return new Value(value + 1);
    }

    public Value decrement() {
        return new Value(value - 1);
    }

    public Value plus(Value otherValue) {
        return new Value(value + otherValue.value);
    }

    public Value minus(Value otherValue) {
        return new Value(value - otherValue.value);
    }

    int asNumber() {
        return value;
    }

    char asCharachter() {
        return (char) value;
    }
}
