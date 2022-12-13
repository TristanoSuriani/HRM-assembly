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
        Indexes.checkCanFetch(idx, SIZE);
        return registers[idx].fetch();
    }

    public void store(int idx, Value value) {
        Indexes.checkCanStore(idx, SIZE);
        registers[idx].store(value);
    }
}
