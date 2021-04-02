package nl.suriani.hrmasm.lib.cpu.chips;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackUnitTest {
	private StackUnit stackUnit;

	@BeforeEach
	void setUp() {
		stackUnit = new StackUnit((short) 4);
	}

	@Test
	void test() {
		stackUnit.push((short) 0);
		stackUnit.push((short) 1);
		stackUnit.push((short) 2);
		stackUnit.push((short) 3);

		assertEquals(3, stackUnit.pop());
		assertEquals(2, stackUnit.pop());
		assertEquals(1, stackUnit.pop());
		assertEquals(0, stackUnit.pop());
	}
}
