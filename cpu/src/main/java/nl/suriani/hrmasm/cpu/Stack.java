package nl.suriani.hrmasm.cpu;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Stack {
    private Deque<Value> deque;

    public Stack() {
        deque = new ArrayDeque<>();
    }

    public void push(Value value) {
        deque.push(value);
    }

    public void pop() {
        deque.pop();
    }

    List<Value> debug() {
        return deque.stream().toList();
    }
}
