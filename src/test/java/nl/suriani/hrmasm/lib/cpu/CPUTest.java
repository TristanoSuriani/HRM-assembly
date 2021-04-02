package nl.suriani.hrmasm.lib.cpu;

import nl.suriani.hrmasm.lib.assembler.MachineLanguageHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {

	private CPU cpu;

	@BeforeEach
	void setUp() {
		cpu = new CPU();
	}

	@Test
	void testStoreData() {
		// todo add
	}

	@Test
	void testNoop() {
		short[] data = new short[] { 12, 9, 15};
		short[] instructions = new short[] { MachineLanguageHelper.inbox(), MachineLanguageHelper.noop() };
		createLoadAndRunProgram(instructions, data);

		assertEquals(12, cpu.dRegister.load());
	}

	@Test
	void testInbox() {
		short[] data = new short[] { 12, 9, 15};
		short instruction = MachineLanguageHelper.inbox();
		createLoadAndRunProgram(instruction, data);

		assertEquals(12, cpu.dRegister.load());
		assertEquals(12, cpu.ram.load(CPU.INBOX_OFFSET));
		assertEquals(0, cpu.ram.load((short) (CPU.INBOX_OFFSET + 1)));
	}

	@Test
	void testOutbox() {
		short[] data = new short[] { 12, 9, 15};
		short instruction = MachineLanguageHelper.outbox();
		presetOutbox(data);
		createLoadAndRunProgram(instruction);

		assertEquals(0, cpu.dRegister.load());
		assertEquals(12, cpu.ram.load(CPU.OUTBOX_OFFSET));
		assertEquals(0, cpu.ram.load((short) (CPU.OUTBOX_OFFSET + 1)));
	}

	private void presetInbox(short[] data) {
		cpu.inbox.write(data);
	}

	private void presetOutbox(short[] data) {
		cpu.outbox.write(data);
	}

	private void createLoadAndRunProgram(short instruction, short[] inboxData) {
		presetInbox(inboxData);
		createLoadAndRunProgram(new short[] { instruction });
	}

	private void createLoadAndRunProgram(short[] instructions, short[] inboxData) {
		presetInbox(inboxData);
		createLoadAndRunProgram(instructions);
	}

	private void createLoadAndRunProgram(short instruction) {
		createAndLoadProgram(new short[] { instruction });
		cpu.start();
	}

	private void createLoadAndRunProgram(short[] instructions) {
		createAndLoadProgram(instructions);
		cpu.start();
	}

	private void createAndLoadProgram(short instruction) {
		short[] program = createProgram(new short[] { instruction });
		cpu.loadProgram(program);
	}

	private void createAndLoadProgram(short[] instructions) {
		short[] program = createProgram(instructions);
		cpu.loadProgram(program);
	}

	private short[] createProgram(short[] instructions) {
		short[] program = new short[instructions.length + 1];
		for (short i = 0; i < instructions.length; i += 1) {
			program[i] = instructions[i];
		}
		short halt = MachineLanguageHelper.halt();
		program[instructions.length] = halt;
		return program;
	}


}
