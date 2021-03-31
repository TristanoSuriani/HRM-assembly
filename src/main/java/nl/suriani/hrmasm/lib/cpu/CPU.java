package nl.suriani.hrmasm.lib.cpu;

public class CPU {
	private final ALU alu;
	private final Clock clock;
	private final RAM ram;
	private final ROM rom;
	private final Register16Bit dRegister;
	private final Register16Bit mRegister;
	private final Register16Bit pcRegister;
	private final StackUnit inbox;
	private final OutboxController outbox;
	private final Register2Bit sRegister;

	private static final short CLOCK_SPEED = 				16738;
	private static final short RAM_SIZE = 					16738;
	private static final short ROM_SIZE = 					4096;
	private static final short INBOX_SIZE = 				64;
	private static final short OUTBOX_SIZE = 				64;
	private static final short INBOX_OFFSET = 				14000;
	private static final short OUTBOX_OFFSET = INBOX_OFFSET + INBOX_SIZE;

	private static final short OFF_STATE = 					0b00;
	private static final short PAUSED_STATE = 				0b01;
	private static final short RUNNING_STATE = 				0b10;
	private static final short HALTED_STATE = 				0b11;

	public static final short AC_BIT_MASK = (short) 		0b1_000000_0000_00000;
	public static final short OPCODE_BITS_MASK =			0b0_000000_1111_00000;
	public static final short ADDRESS_BITS_MASK = 			0b0_000000_0000_11111;
	public static final short DATA_BITS_MASK = 				0b0_111111_1111_11111;
	public static final short AC_BIT_SHIFT = 				15;
	public static final short OPCODE_BITS_SHIFT = 			5;
	public static final short ADDRESS_BITS_SHIFT = 			0;
	public static final short DATA_BITS_SHIFT = 			0;

	public static final short NOOP_OPCODE = 				0x0;
	public static final short INBOX_OPCODE = 				0x1;
	public static final short OUTBOX_OPCODE = 				0x2;
	public static final short JUMP_OPCODE = 				0x3;
	public static final short JUMP0_OPCODE = 				0x4;
	public static final short JUMPN_OPCODE = 				0x5;
	public static final short COPYFROM_OPCODE = 			0x6;
	public static final short COPYTO_OPCODE = 				0x7;
	public static final short COPYFROM_STAR_OPCODE = 		0x8;
	public static final short COPYTO_STAR_OPCODE = 			0x9;
	public static final short BUMP_PLUS_OPCODE = 			0xa;
	public static final short BUMP_MIN_OPCODE = 			0xb;
	public static final short ADD_OPCODE = 					0xc;
	public static final short SUB_OPCODE = 					0xd;
	public static final short PAUSE_OPCODE = 				0xe;
	public static final short HALT_OPCODE = 				0xf;

	public CPU() {
		alu = new ALU();
		clock = new Clock(CLOCK_SPEED);
		ram = new RAM(RAM_SIZE);
		rom = new ROM(ROM_SIZE);
		dRegister = new Register16Bit();
		mRegister = new Register16Bit();
		pcRegister = new Register16Bit();
		inbox = new StackUnit(INBOX_SIZE);
		outbox = new OutboxController(OUTBOX_SIZE, OUTBOX_OFFSET);
		sRegister = new Register2Bit();
	}

	public void loadProgram(short[] instructions) {
		rom.flash(instructions);
		setWaitingState();
	}

	public void start() {
		setRunningState();
		runInstructions();
	}

	private void runInstructions() {
		while (sRegister.load() == RUNNING_STATE) {
			short pc = pcRegister.load();
			short instruction = rom.load(pc);
			short acBit = getAcBit(instruction);
			if (acBit == 0b0) {
				short opcode = getOpcode(instruction);
				short address = getAddress(instruction);
				handleCInstruction(opcode, address);
			} else if (acBit == 0b1) {
				short data = getData(instruction);
				handleAInstruction(data);
			}
			clock.tick();
		}
	}

	private void handleAInstruction(short data) {
		dRegister.store(data);
	}

	private void handleCInstruction(short opcode, short address) {
		switch (opcode) {
			case NOOP_OPCODE:
				incrementPcRegister();
				break;

			case INBOX_OPCODE:
				dRegister.store(inbox.pop());
				incrementPcRegister();
				break;

			case OUTBOX_OPCODE:
				dRegister.reset();
				outbox.read(ram);
				incrementPcRegister();
				break;

			case JUMP_OPCODE:
				// TODO define
				// pcRegister.load(...)
				break;

			case JUMP0_OPCODE:
				if (dRegister.load() == 0) {
					// TODO define
					// pcRegister.load(...)
				}
				break;

			case JUMPN_OPCODE:
				if (dRegister.load() < 0) {
					// TODO define
					// pcRegister.load(...)
				}
				break;

			case COPYFROM_OPCODE:
				mRegister.store(address);
				dRegister.store(ram.load(mRegister.load()));
				incrementPcRegister();
				break;

			case COPYTO_OPCODE:
				mRegister.store(address);
				ram.store(mRegister.load(), dRegister.load());
				incrementPcRegister();
				break;

			case COPYFROM_STAR_OPCODE:
				// TODO define
				incrementPcRegister();
				break;

			case COPYTO_STAR_OPCODE:
				// TODO define
				incrementPcRegister();
				break;

			case BUMP_PLUS_OPCODE:
				ram.store(address, (short) (ram.load(address) + 1));
				dRegister.store(ram.load(address));
				incrementPcRegister();
				break;

			case BUMP_MIN_OPCODE:
				ram.store(address, (short) (ram.load(address) - 1));
				dRegister.store(ram.load(address));
				incrementPcRegister();
				break;

			case ADD_OPCODE:
				short addA = dRegister.load();
				short addB = ram.load(address);
				alu.store(addA, addB);
				dRegister.store(alu.loadSum());
				incrementPcRegister();
				break;

			case SUB_OPCODE:
				short subA = dRegister.load();
				short subB = ram.load(address);
				alu.store(subA, subB);
				dRegister.store(alu.loadSub());
				incrementPcRegister();
				break;

			case PAUSE_OPCODE:
				sRegister.store0b01();
				incrementPcRegister();
				break;

			case HALT_OPCODE:
				sRegister.store0b11();
				pcRegister.reset();
				break;
		}
	}

	private void incrementPcRegister() {
		pcRegister.store((short) (pcRegister.load() + 1));
	}

	private short getAddress(short instruction) {
		return extractBits(instruction, ADDRESS_BITS_MASK, ADDRESS_BITS_SHIFT);
	}

	private short getOpcode(short instruction) {
		return extractBits(instruction, OPCODE_BITS_MASK, OPCODE_BITS_SHIFT);
	}

	private short getAcBit(short instruction) {
		return extractBits(instruction, AC_BIT_MASK, AC_BIT_SHIFT);
	}

	private short getData(short instruction) {
		return extractBits(instruction, DATA_BITS_MASK, DATA_BITS_SHIFT);
	}

	private void setRunningState() {
		sRegister.store0b10();
	}

	private void setWaitingState() {
		sRegister.store0b01();
	}

	public void setHaltedState() {
		sRegister.store0b11();
	}

	private short extractBits(short instruction, short mask, short shift) {
		return (short) ((instruction & mask) >> shift);
	}
}
