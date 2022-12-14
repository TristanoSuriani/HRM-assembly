package nl.suriani.hrmasm.cpu;

import nl.suriani.hrmasm.program.Program;
import nl.suriani.hrmasm.program.Instruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static nl.suriani.hrmasm.program.Instruction.*;
import static nl.suriani.hrmasm.program.StatementType.*;
import static org.junit.jupiter.api.Assertions.*;

class CPUTest {

    private CPU cpu;

    @BeforeEach
    void setUp() {
        cpu = new CPU();
    }

    @Test
    void hrm01() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(OUTBOX),
                statement(INBOX),
                statement(OUTBOX),
                statement(INBOX),
                statement(OUTBOX)
        );

        givenValuesArePushedIntoInbox(1, 2, 3);
        whenProgramIsExecutedWithDebugOutput();
        thenOutboxContainsValues(1, 2, 3);
    }

    private void givenCpuIsLoadedWithProgram(Instruction... instructions) {
        cpu.load(new Program(Arrays.asList(instructions)));
    }

    private void givenValueIsPushedIntoInbox(int value) {
        cpu.pushIntoInbox(new Value(value));
    }

    private void givenValuesArePushedIntoInbox(int... values) {
        Arrays.stream(values)
                .mapToObj(Value::new)
                .forEach(value -> cpu.pushIntoInbox(value));
    }

    private void whenProgramIsExecuted() {
        cpu.execute();
    }

    private void whenProgramIsExecutedWithDebugOutput() {
        cpu.debug().execute();
    }

    private void thenOutboxContainsValues(int... values) {
        var debugger = cpu.getDebugger();
        var valuesOutbox = debugger.valuesOutbox().stream()
                .map(Value::asNumber)
                .toArray();

        var expectedValues = Arrays.stream(values)
                .boxed()
                .toArray();

        assertArrayEquals(expectedValues, valuesOutbox);
    }

}