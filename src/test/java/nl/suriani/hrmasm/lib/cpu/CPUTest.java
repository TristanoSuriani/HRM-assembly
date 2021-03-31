package nl.suriani.hrmasm.lib.cpu;

import nl.suriani.hrmasm.lib.assembler.MachineLanguageHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {

	@Test
	void start() {
		CPU cpu = new CPU();
		short instruction = (short) 0b0_111111_0101_11010; // 0 8 16
		MachineLanguageHelper.storeData((short) 0b100);
		short[] instructions = new short[]{instruction};
		cpu.loadProgram(instructions);
		cpu.start();
	}
}
