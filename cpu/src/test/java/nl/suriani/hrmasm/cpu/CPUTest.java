package nl.suriani.hrmasm.cpu;

import nl.suriani.hrmasm.program.Program;
import nl.suriani.hrmasm.program.Instruction;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class CPUTest {

    protected CPU cpu;

    @BeforeEach
    void setUp() {
        cpu = new CPU();
    }

    

    protected void givenCpuIsLoadedWithProgram(Instruction... instructions) {
        cpu.load(new Program(Arrays.asList(instructions)));
    }

    protected void givenNumbersAreAddedToInbox(int... values) {
        Arrays.stream(values)
                .mapToObj(Value::new)
                .forEach(value -> cpu.addToInbox(value));
    }

    protected void givenCharachterIsPushedIntoRegister(int register, char charachter) {
        cpu.storeIntoRegister(register, new Value(charachter));
    }

    protected void givenCharachtersAreAddedToInbox(Character... values) {
        Arrays.stream(values)
                .map(Value::new)
                .forEach(value -> cpu.addToInbox(value));
    }

    protected void whenProgramIsExecuted() {
        cpu = cpu.execute();
    }

    protected void whenProgramIsExecutedWithDebugOutput() {
        cpu = cpu.debug().execute();
    }

    protected void thenOutboxContainsNumbers(int... values) {
        var debugger = cpu.getDebugger();
        var valuesOutbox = debugger.valuesOutbox().stream()
                .map(Value::asNumber)
                .toArray();

        var expectedValues = Arrays.stream(values)
                .boxed()
                .toArray();

        assertArrayEquals(expectedValues, valuesOutbox);
    }

    protected void thenOutboxContainsCharacters(Character... values) {
        var debugger = cpu.getDebugger();
        var valuesOutbox = debugger.valuesOutbox().stream()
                .map(Value::asCharachter)
                .toArray();

        var expectedValues = Arrays.stream(values)
                .toArray();

        assertArrayEquals(expectedValues, valuesOutbox);
    }

    protected void thenOutboxContainsNoValues() {
        var debugger = cpu.getDebugger();
        assertTrue(debugger.valuesOutbox().isEmpty());
    }

    protected void thenCPUIsHalted() {
        assertTrue(cpu.isHalted());
    }

    protected void thenCPUIsHaltedAbnormally() {
        assertTrue(cpu.isHaltedAbnormally());
    }
}