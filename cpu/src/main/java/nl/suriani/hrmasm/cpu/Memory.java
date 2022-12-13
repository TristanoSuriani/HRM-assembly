package nl.suriani.hrmasm.cpu;

import java.util.stream.Stream;

public class Memory {
    static final int SIZE = 65536;
    private final MemoryCell[] memoryCells = new MemoryCell[SIZE];

    public Memory() {
        Stream.iterate(0, i -> i + 1)
                .limit(SIZE)
                .forEach(i -> memoryCells[i] = new MemoryCell());
    }

    public Value fetch(int address) {
        Indexes.checkCanFetch(address, SIZE);
        return memoryCells[address].fetch();
    }

    public void store(int address, Value value) {
        Indexes.checkCanStore(address, SIZE);
        memoryCells[address].store(value);
    }
}
