package nl.suriani.hrmasm.lib.assembler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineLanguageHelperTest {
	@Test
	void testStoreData() {
		short data = 0b100;
		short instruction = MachineLanguageHelper.storeData(data);

		assertEquals((short) 0b1_000000_0000_00100, instruction);
	}

	@Test
	void testNoop() {
		short instruction = MachineLanguageHelper.noop();
		assertEquals((short) 0b0_000000_0000_00000, instruction);
	}

	@Test
	void testInbox() {
		short instruction = MachineLanguageHelper.inbox();
		assertEquals((short) 0b0_000000_0001_00000, instruction);
	}

	@Test
	void testOutbox() {
		short instruction = MachineLanguageHelper.outbox();
		assertEquals((short) 0b0_000000_0010_00000, instruction);
	}
}
