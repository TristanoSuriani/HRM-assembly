package nl.suriani.hrmasm.lib.cpu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Register2BitTest {
	private Register2Bit register2Bit;

	@BeforeEach
	void setUp() {
		register2Bit = new Register2Bit();
	}

	@Test
	void test() {
		Assertions.assertEquals(0, register2Bit.load());
		Assertions.assertEquals(0b00, register2Bit.load());

		register2Bit.store0b00();
		Assertions.assertEquals(0, register2Bit.load());
		Assertions.assertEquals(0b00, register2Bit.load());

		register2Bit.store0b01();
		Assertions.assertEquals(1, register2Bit.load());
		Assertions.assertEquals(0b01, register2Bit.load());

		register2Bit.store0b10();
		Assertions.assertEquals(2, register2Bit.load());
		Assertions.assertEquals(0b10, register2Bit.load());

		register2Bit.store0b11();
		Assertions.assertEquals(3, register2Bit.load());
		Assertions.assertEquals(0b11, register2Bit.load());
	}
}
