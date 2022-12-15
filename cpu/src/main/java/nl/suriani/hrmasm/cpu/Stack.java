package nl.suriani.hrmasm.cpu;

import java.util.*;

public class Stack {
    private final Deque<Value> deque;

    public Stack() {
        deque = new ArrayDeque<>();
    }

    public void push(Value value) {
        deque.push(value);
    }

    public void add(Value value) {
        deque.add(value);
    }

    public Optional<Value> pop() {
        if (deque.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(deque.pop());
    }

    List<Value> debug() {
        var list = new ArrayList<>(deque);
        Collections.reverse(list);
        return list;
    }
}
