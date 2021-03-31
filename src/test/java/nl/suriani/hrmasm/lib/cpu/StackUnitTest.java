package nl.suriani.hrmasm.lib.cpu;

import org.junit.jupiter.api.Assertions;
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

		Assertions.assertEquals(3, stackUnit.pop());
		Assertions.assertEquals(2, stackUnit.pop());
		Assertions.assertEquals(1, stackUnit.pop());
		Assertions.assertEquals(0, stackUnit.pop());
	}
}
