package nl.suriani.hrmasm.lib.cpu.chips;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ROMTest {
	private ROM rom;

	@BeforeEach
	void setUp() {
		rom = new ROM((short) 2);
	}

	@Test
	void test() {
		short[] instructions = new short[] { 1, 2, 3, 4 };
		rom.flash(instructions);

		assertEquals(1, rom.load((short) 0));
		assertEquals(2, rom.load((short) 1));
	}
}
