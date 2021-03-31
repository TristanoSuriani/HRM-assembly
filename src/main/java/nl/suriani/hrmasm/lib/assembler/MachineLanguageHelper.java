package nl.suriani.hrmasm.lib.assembler;

import nl.suriani.hrmasm.lib.cpu.CPU;

import static nl.suriani.hrmasm.lib.cpu.CPU.*;

public class MachineLanguageHelper {
	private static final short A_BIT 	= 0b1;
	private static final short C_BIT 	= 0b0;

	public static short storeData(short data) {
		short instruction = addToInstruction(A_BIT, AC_BIT_MASK, AC_BIT_SHIFT);
		instruction = addToInstruction(instruction, data, DATA_BITS_MASK, DATA_BITS_SHIFT);
		return instruction;
	}

	public static short noop() {
		short instruction = addToInstruction(C_BIT, AC_BIT_MASK, AC_BIT_SHIFT);
		instruction = addToInstruction(instruction, NOOP_OPCODE, DATA_BITS_MASK, DATA_BITS_SHIFT);
		return instruction;
	}

	public static short inbox() {
		short instruction = addToInstruction(C_BIT, AC_BIT_MASK, AC_BIT_SHIFT);
		instruction = addToInstruction(instruction, INBOX_OPCODE, OPCODE_BITS_MASK, OPCODE_BITS_SHIFT);
		return instruction;
	}

	public static short outbox() {
		short instruction = addToInstruction(C_BIT, AC_BIT_MASK, AC_BIT_SHIFT);
		instruction = addToInstruction(instruction, OUTBOX_OPCODE, OPCODE_BITS_MASK, OPCODE_BITS_SHIFT);
		return instruction;
	}

	private static short addToInstruction(short instruction, short bits, short mask, short shift) {
		return (short) (instruction | (bits << shift) & mask);
	}

	private static short addToInstruction(short bits, short mask, short shift) {
		return addToInstruction((short) 0b0_000000_0000_00000, bits, mask, shift);
	}
}
