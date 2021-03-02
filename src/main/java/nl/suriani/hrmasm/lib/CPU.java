package nl.suriani.hrmasm.lib;

import nl.suriani.hrmasm.lib.parser.AST;
import nl.suriani.hrmasm.lib.parser.ParsedStatement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class CPU {
	private String[] registers;
	private AST program;
	private Deque<String> inbox;
	private Deque<String> outbox;
	private Deque<Integer> callStack;
	private String RDI;
	private Map<String, String> memory;
	private int cycles = 0;
	private int programCounter = 0;
	private String cache;
	private ProgramState programState;
	private boolean compatibilityMode;

	public CPU() {
		reset();
	}

	public CPU(boolean compatibilityMode) {
		reset();
		this.compatibilityMode = compatibilityMode;
	}

	public void addToInbox(String element) {
		if(compatibilityMode) {
			inbox.add(element);
		} else {
			inbox.push(element);
		}
	}

	public String readInbox() {
		return inbox.pop();
	}

	public int getInboxSize() {
		return inbox.size();
	}

	public void addToOutbox(String element) {
		if(compatibilityMode) {
			outbox.add(element);
		} else {
			outbox.push(element);
		}
	}

	public String readOutbox() {
		return outbox.pop();
	}

	public int getOutboxSize() {
		return outbox.size();
	}

	public String printOutbox() {
		return outbox.stream()
				.reduce((prev, next) -> prev + " " + next)
				.orElse("");
	}

	public String getFromRegister(int registerNumber) {
		return registers[registerNumber];
	}

	public void setToRegister(int registerNumber, String value) {
		registers[registerNumber] = value;
	}

	public void reset() {
		registers = new String[32];
		program = null;
		memory = new HashMap<>();
		RDI = null;
		inbox = new ArrayDeque<>();
		outbox = new ArrayDeque<>();
		callStack = new ArrayDeque<>();
		cache = null;
		cycles = 0;
		programCounter = 0;

		programState = ProgramState.READY;
	}

	public ParsedStatement getInstruction() {
		return program.getStatements().get(programCounter);
	}

	public boolean hasNextInstruction() {
		return programCounter < program.getStatements().size();
	}

	public void updateCycles() {
		cycles += 1;
	}

	public void updateProgramCounter() {
		programCounter += 1;
	}

	public void updateProgramCounter(int nextInstruction) {
		programCounter = nextInstruction;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public ProgramState getProgramState() {
		return programState;
	}

	public void setProgramState(ProgramState programState) {
		this.programState = programState;
	}

	public void setProgram(AST program) {
		this.program = program;
	}

	public int getProgramCounter() {
		return programCounter;
	}

	public int getCycles() {
		return cycles;
	}

	public AST getProgram() {
		return program;
	}

	public String getRDI() {
		return RDI;
	}

	public void setRDI(String RDI) {
		this.RDI = RDI;
	}

	public void putToMemory(String key, String value) {
		memory.put(key, value);
	}

	public String getFromMemory(String key) {
		return memory.get(key);
	}

	public void addToCallStack(int instructionNumber) {
		callStack.push(instructionNumber);
	}

	public int readCallStack() {
		return callStack.pop();
	}
}
