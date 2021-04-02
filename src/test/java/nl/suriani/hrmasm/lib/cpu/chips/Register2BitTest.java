package nl.suriani.hrmasm.lib.cpu.chips;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Register2BitTest {
	private Register2Bit register2Bit;

	@BeforeEach
	void setUp() {
		register2Bit = new Register2Bit();
	}

	@Test
	void test() {
		assertEquals(0, register2Bit.load());
		assertEquals(0b00, register2Bit.load());

		register2Bit.store0b00();
		assertEquals(0, register2Bit.load());
		assertEquals(0b00, register2Bit.load());

		register2Bit.store0b01();
		assertEquals(1, register2Bit.load());
		assertEquals(0b01, register2Bit.load());

		register2Bit.store0b10();
		assertEquals(2, register2Bit.load());
		assertEquals(0b10, register2Bit.load());

		register2Bit.store0b11();
		assertEquals(3, register2Bit.load());
		assertEquals(0b11, register2Bit.load());
	}
}
