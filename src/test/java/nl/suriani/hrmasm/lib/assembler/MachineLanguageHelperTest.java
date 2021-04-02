package nl.suriani.hrmasm.lib.assembler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineLanguageHelperTest {
	@Test
	void testStoreData() {
		short data = 0b100;
		short instruction = MachineLanguageHelper.storeData(data);

		assertEquals((short) 0b1_000000000000100, instruction);
	}

	@Test
	void testJump() {
		short line = 0b10000;
		short instruction = MachineLanguageHelper.jump(line);

		assertEquals(0b0_0_00_000000010000, instruction);
	}

	@Test
	void testJump0() {
		short line = 0b11000;
		short instruction = MachineLanguageHelper.jump0(line);

		assertEquals(0b0_0_01_000000011000, instruction);
	}

	@Test
	void testJumpN() {
		short line = 0b10001;
		short instruction = MachineLanguageHelper.jumpN(line);

		assertEquals(0b0_0_10_000000010001, instruction);
	}

	@Test
	void testJumpP() {
		short line = 0b10100;
		short instruction = MachineLanguageHelper.jumpP(line);

		assertEquals(0b0_0_11_000000010100, instruction);
	}

	@Test
	void testNoop() {
		short instruction = MachineLanguageHelper.noop();
		assertEquals(0b0_1_0000_0000000000, instruction);
	}

	@Test
	void testInbox() {
		short instruction = MachineLanguageHelper.inbox();
		assertEquals(0b0_1_0001_0000000000, instruction);
	}

	@Test
	void testOutbox() {
		short instruction = MachineLanguageHelper.outbox();
		assertEquals(0b0_1_0010_0000000000, instruction);
	}

	@Test
	void testCopyfrom() {
		short address = 0b11;
		short instruction = MachineLanguageHelper.copyfrom(address);
		assertEquals(0b0_1_0011_0000000011, instruction);
	}

	@Test
	void testCopyto() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.copyto(address);
		assertEquals(0b0_1_0100_0000000111, instruction);
	}

	@Test
	void testCopyfromStar() {
		short address = 0b11;
		short instruction = MachineLanguageHelper.copyfromStar(address);
		assertEquals(0b0_1_0101_0000000011, instruction);
	}

	@Test
	void testCopytoStar() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.copytoStar(address);
		assertEquals(0b0_1_0110_0000000111, instruction);
	}

	@Test
	void testBumpPlus() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.bumpPlus(address);
		assertEquals(0b0_1_0111_0000000111, instruction);
	}

	@Test
	void tesBumpMin() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.bumpMin(address);
		assertEquals(0b0_1_1000_0000000111, instruction);
	}

	@Test
	void testAdd() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.add(address);
		assertEquals(0b0_1_1001_0000000111, instruction);
	}

	@Test
	void testSub() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.sub(address);
		assertEquals(0b0_1_1010_0000000111, instruction);
	}

	@Test
	void testMul() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.mul(address);
		assertEquals(0b0_1_1011_0000000111, instruction);
	}

	@Test
	void testDiv() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.div(address);
		assertEquals(0b0_1_1100_0000000111, instruction);
	}

	@Test
	void testMod() {
		short address = 0b111;
		short instruction = MachineLanguageHelper.mod(address);
		assertEquals(0b0_1_1101_0000000111, instruction);
	}
}
