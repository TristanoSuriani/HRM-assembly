package nl.suriani.hrmasm.cpu;

import java.util.Optional;
import java.util.stream.Stream;

public class Stacks {
    static final int SIZE = 8;

    private final Stack[] stacks = new Stack[SIZE];

    public Stacks() {
        Stream.iterate(0, i -> i + 1)
                .limit(SIZE)
                .forEach(i -> stacks[i] = new Stack());
    }

    public void push(int idx, Value value) {
        Indexes.checkCanStore(idx, SIZE);
        stacks[idx].push(value);
    }

    public Optional<Value> pop(int idx) {
        Indexes.checkCanFetch(idx, SIZE);
        return stacks[idx].pop();
    }
}
