package nl.suriani.hrmasm.lib.assembler;

import static nl.suriani.hrmasm.lib.cpu.CPU.*;

public class MachineLanguageHelper {
	public static short storeData(short data) {
		return createAInstruction(data);
	}

	public static short noop() {
		return createCInstruction(NOOP_OPCODE);
	}

	public static short inbox() {
		return createCInstruction(INBOX_OPCODE);
	}

	public static short outbox() {
		return createCInstruction(OUTBOX_OPCODE);
	}

	public static short jump(short line) {
		return createBInstruction(JUMP_CODE, line);
	}

	public static short jump0(short line) {
		return createBInstruction(JUMP0_CODE, line);
	}

	public static short jumpN(short line) {
		return createBInstruction(JUMPN_CODE, line);
	}

	public static short jumpP(short line) {
		return createBInstruction(JUMPP_CODE, line);
	}

	public static short copyfrom(short address) {
		return createCInstruction(COPYFROM_OPCODE, address);
	}

	public static short copyto(short address) {
		return createCInstruction(COPYTO_OPCODE, address);
	}

	public static short copyfromStar(short address) {
		return createCInstruction(COPYFROM_STAR_OPCODE, address);
	}

	public static short copytoStar(short address) {
		return createCInstruction(COPYTO_STAR_OPCODE, address);
	}

	public static short bumpPlus(short address) {
		return createCInstruction(BUMP_PLUS_OPCODE, address);
	}

	public static short bumpMin(short address) {
		return createCInstruction(BUMP_MIN_OPCODE, address);
	}

	public static short add(short address) {
		return createCInstruction(ADD_OPCODE, address);
	}

	public static short sub(short address) {
		return createCInstruction(SUB_OPCODE, address);
	}

	public static short mul(short address) {
		return createCInstruction(MUL_OPCODE, address);
	}

	public static short div(short address) {
		return createCInstruction(DIV_OPCODE, address);
	}

	public static short mod(short address) {
		return createCInstruction(MOD_OPCODE, address);
	}

	public static short pause() {
		return createCInstruction(PAUSE_OPCODE);
	}

	public static short halt() {
		return createCInstruction(HALT_OPCODE);
	}

	private static short createAInstruction(short data) {
		short instruction = addToInstruction(A_BIT, A_BC_BIT_MASK, A_BC_BIT_SHIFT);
		instruction = addToInstruction(instruction, data, DATA_BITS_MASK, DATA_BITS_SHIFT);
		return instruction;
	}

	private static short createBInstruction(short jumpcode, short line) {
		short instruction = addToInstruction(BC_BIT, A_BC_BIT_MASK, A_BC_BIT_SHIFT);
		instruction = addToInstruction(instruction, B_BIT, B_C_BIT_MASK, B_C_BIT_SHIFT);
		instruction = addToInstruction(instruction, jumpcode, JUMP_BITS_MASK, JUMP_BITS_SHIFT);
		instruction = addToInstruction(instruction, line, LINE_BITS_MASK, LINE_BITS_SHIFT);
		return instruction;
	}

	private static short createCInstruction(short opcode, short address) {
		short instruction = addToInstruction(createCInstruction(opcode), address, ADDRESS_BITS_MASK, ADDRESS_BITS_SHIFT);
		return instruction;
	}

	private static short createCInstruction(short opcode) {
		short instruction = addToInstruction(BC_BIT, A_BC_BIT_MASK, A_BC_BIT_SHIFT);
		instruction = addToInstruction(instruction, C_BIT, B_C_BIT_MASK, B_C_BIT_SHIFT);
		instruction = addToInstruction(instruction, opcode, OPCODE_BITS_MASK, OPCODE_BITS_SHIFT);
		return instruction;
	}

	private static short addToInstruction(short instruction, short bits, short mask, short shift) {
		return (short) (instruction | (bits << shift) & mask);
	}

	private static short addToInstruction(short bits, short mask, short shift) {
		return addToInstruction((short) 0b0_000000_0000_00000, bits, mask, shift);
	}
}
