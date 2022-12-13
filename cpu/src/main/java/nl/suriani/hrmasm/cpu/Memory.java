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
        checkCanFetch(address);
        return memoryCells[address].fetch();
    }

    public void store(int address, Value value) {
        checkCanStore(address);
        memoryCells[address].store(value);
    }

    private void checkCanFetch(int address) {
        if (isAddressOutOfBound(address)) {
            throw new CannotFetchException();
        }
    }

    private void checkCanStore(int address) {
        if (isAddressOutOfBound(address)) {
            throw new CannotStoreException();
        }
    }

    private boolean isAddressOutOfBound(int address) {
        return address < 0 || address >= SIZE;
    }
}
