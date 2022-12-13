package nl.suriani.hrmasm.cpu;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class Stack {
    private final Deque<Value> deque;

    public Stack() {
        deque = new ArrayDeque<>();
    }

    public void push(Value value) {
        deque.push(value);
    }

    public Optional<Value> pop() {
        if (deque.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(deque.pop());
    }

    List<Value> debug() {
        return deque.stream().toList();
    }
}
