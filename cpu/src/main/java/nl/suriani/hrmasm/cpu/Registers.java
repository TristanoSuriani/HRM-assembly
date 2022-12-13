package nl.suriani.hrmasm.cpu;

import java.util.stream.Stream;

public class Registers {
    static final int SIZE = 32;
    private final Register[] registers = new Register[SIZE];

    public Registers() {
        Stream.iterate(0, i -> i + 1)
                .limit(SIZE)
                .forEach(i -> registers[i] = new Register());
    }

    public Value fetch(int idx) {
        checkCanFetch(idx);
        return registers[idx].fetch();
    }

    public void store(int idx, Value value) {
        checkCanStore(idx);
        registers[idx].store(value);
    }

    private void checkCanFetch(int idx) {
        if (isIndexOutOfBound(idx)) {
            throw new CannotFetchException();
        }
    }

    private void checkCanStore(int idx) {
        if (isIndexOutOfBound(idx)) {
            throw new CannotStoreException();
        }
    }

    private boolean isIndexOutOfBound(int idx) {
        return idx < 0 || idx >= SIZE;
    }
}
