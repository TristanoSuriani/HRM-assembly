package nl.suriani.hrmasm.lib.cpu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RAMTest {

	private RAM ram;

	@BeforeEach
	void setUp() {
		ram = new RAM((short) 2);
	}

	@Test
	void test() {
		ram.store((short) 0, (short) 1);
		ram.store((short) 1, (short) 2);

		Assertions.assertEquals(1, ram.load((short) 0));
		Assertions.assertEquals(2, ram.load((short) 1));
	}
}
