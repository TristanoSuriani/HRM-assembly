package nl.suriani.hrmasm.cpu;

public record Value(int value) {
    public Value() {
        this(0);
    }

    public Value(char value) {
        this((int) value);
    }

    int asNumber() {
        return value;
    }

    char asCharachter() {
        return (char) value;
    }
}
