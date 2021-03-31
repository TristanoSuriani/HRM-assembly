package nl.suriani.hrmasm.lib.cpu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ALUTest {
	private ALU alu;

	@BeforeEach
	void setUp() {
		alu = new ALU();
	}

	@Test
	void test() {
		short a = 5;
		short b = 3;
		alu.store(a, b);
		Assertions.assertEquals(8, alu.loadSum());
		Assertions.assertEquals(2, alu.loadSub());
	}
}
