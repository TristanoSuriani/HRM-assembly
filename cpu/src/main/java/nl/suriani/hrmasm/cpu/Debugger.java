package nl.suriani.hrmasm.cpu;

import java.util.List;

public record Debugger(List<Value> valuesInbox, List<Value> valuesOutbox) {
}
