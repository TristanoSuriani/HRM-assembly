package nl.suriani.hrmasm.cpu;

import java.util.List;
import java.util.Optional;

public class Inbox {
    private final Stack stack;

    public Inbox() {
        stack = new Stack();
    }

    public void push(Value value) {
        stack.push(value);
    }

    void add(Value value) {
        stack.add(value);
    }

    public Optional<Value> pop() {
        return stack.pop();
    }

    List<Value> debug() {
        return stack.debug();
    }
}
