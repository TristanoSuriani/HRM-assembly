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
        givenNumbersAreAddedToInbox(1, 2, 3);
        whenProgramIsExecutedWithDebugOutput();
        thenOutboxContainsNoValues();
    }

    @Test
    void infiniteLoopDoesntBlockTheMachineForever() {
        givenCpuIsLoadedWithProgram(statement(JUMP, "0"));
        whenProgramIsExecutedWithDebugOutput();
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

        givenNumbersAreAddedToInbox(1, 2, 3);
        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsNumbers(1, 2, 3);
    }

    @Test
    void hrm02() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(OUTBOX),
                statement(JUMP, "0")
        );

        givenCharachtersAreAddedToInbox('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsCharacters('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
    }

    @Test
    void hrm03() {
        givenCpuIsLoadedWithProgram(
                statement(COPY_FROM, "4"),
                statement(OUTBOX),
                statement(COPY_FROM, "0"),
                statement(OUTBOX),
                statement(COPY_FROM, "3"),
                statement(OUTBOX)
        );

        givenNumbersAreAddedToInbox(-99, -99, -99, -99);
        givenCharachterIsPushedIntoRegister(0, 'U');
        givenCharachterIsPushedIntoRegister(1, 'J');
        givenCharachterIsPushedIntoRegister(2, 'X');
        givenCharachterIsPushedIntoRegister(3, 'G');
        givenCharachterIsPushedIntoRegister(4, 'B');
        givenCharachterIsPushedIntoRegister(5, 'E');

        whenProgramIsExecutedWithDebugOutput();
        thenCPUIsHalted();
        thenOutboxContainsCharacters('B', 'U', 'G');
    }

    private void givenCpuIsLoadedWithProgram(Instruction... instructions) {
        cpu.load(new Program(Arrays.asList(instructions)));
    }

    private void givenNumbersAreAddedToInbox(int... values) {
        Arrays.stream(values)
                .mapToObj(Value::new)
                .forEach(value -> cpu.addToInbox(value));
    }

    private void givenCharachterIsPushedIntoRegister(int register, char charachter) {
        cpu.storeIntoRegister(register, new Value(charachter));
    }

    private void givenCharachtersAreAddedToInbox(Character... values) {
        Arrays.stream(values)
                .map(Value::new)
                .forEach(value -> cpu.addToInbox(value));
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

    private void thenOutboxContainsCharacters(Character... values) {
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

    private void thenCPUIsHalted() {
        assertTrue(cpu.isHalted());
    }

    private void thenCPUIsHaltedAbnormally() {
        assertTrue(cpu.isHaltedAbnormally());
    }
}