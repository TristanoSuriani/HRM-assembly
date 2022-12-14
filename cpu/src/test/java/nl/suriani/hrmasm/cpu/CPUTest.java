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
    void noProgramLoaded() {
        givenNumbersArePushedIntoInbox(1, 2, 3);
        whenProgramIsExecutedWithDebugOutput();
        thenOutboxContainsNoValues();
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

        givenNumbersArePushedIntoInbox(1, 2, 3);
        whenProgramIsExecutedWithDebugOutput();
        thenOutboxContainsNumbers(1, 2, 3);
    }

    @Test
    void hrm02() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(OUTBOX),
                statement(JUMP, "0")
        );

        givenCharachtersArePushedIntoInbox('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
        whenProgramIsExecutedWithDebugOutput();
        thenOutboxContainsCharachters('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
    }

    private void givenCpuIsLoadedWithProgram(Instruction... instructions) {
        cpu.load(new Program(Arrays.asList(instructions)));
    }

    private void givenValueIsPushedIntoInbox(int value) {
        cpu.pushIntoInbox(new Value(value));
    }

    private void givenNumbersArePushedIntoInbox(int... values) {
        Arrays.stream(values)
                .mapToObj(Value::new)
                .forEach(value -> cpu.pushIntoInbox(value));
    }

    private void givenCharachtersArePushedIntoInbox(Character... values) {
        Arrays.stream(values)
                .map(Value::new)
                .forEach(value -> cpu.pushIntoInbox(value));
    }

    private void whenProgramIsExecuted() {
        cpu.execute();
    }

    private void whenProgramIsExecutedWithDebugOutput() {
        cpu.debug().execute();
    }

    private void thenOutboxContainsNumbers(int... values) {
        var debugger = cpu.getDebugger();
        var valuesOutbox = debugger.valuesOutbox().stream()
                .map(Value::asNumber)
                .toArray();

        var expectedValues = Arrays.stream(values)
                .boxed()
                .toArray();

        assertArrayEquals(expectedValues, valuesOutbox);
    }

    private void thenOutboxContainsCharachters(Character... values) {
        var debugger = cpu.getDebugger();
        var valuesOutbox = debugger.valuesOutbox().stream()
                .map(Value::asCharachter)
                .toArray();

        var expectedValues = Arrays.stream(values)
                .toArray();

        assertArrayEquals(expectedValues, valuesOutbox);
    }

    private void thenOutboxContainsNoValues() {
        var debugger = cpu.getDebugger();
        assertTrue(debugger.valuesOutbox().isEmpty());
    }

}