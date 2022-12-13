package nl.suriani.hrmasm.cpu;

import java.util.List;
import java.util.Optional;

public class Outbox {
    private final Stack stack;

    public Outbox() {
        stack = new Stack();
    }

    public void push(Value value) {
        stack.push(value);
    }

    public Optional<Value> pop() {
        return stack.pop();
    }

    List<Value> debug() {
        return stack.debug();
    }
}
