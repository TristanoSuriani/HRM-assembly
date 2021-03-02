package nl.suriani.hrmasm.lib;

import lombok.AllArgsConstructor;
import nl.suriani.hrmasm.lib.parser.AST;
import nl.suriani.hrmasm.lib.parser.Literal;
import nl.suriani.hrmasm.lib.parser.ParsedStatement;
import nl.suriani.hrmasm.lib.parser.Value;
import nl.suriani.hrmasm.lib.parser.ValueType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.suriani.hrmasm.lib.parser.ParserRuleType.LABEL;

@AllArgsConstructor
public class Interpreter {
	private CPU cpu;
	private final static String LOG_TEMPLATE = "[%s] (%s) - %s";

	public void run(AST program) {
		Map<String, Integer> labels = prefillLabels();
		Map<String, Integer> aliases = new HashMap<>();
		while (cpu.hasNextInstruction() && cpu.getProgramState() != ProgramState.TERMINATED) {
			var statement = getStatement();
			System.out.println(String.format(LOG_TEMPLATE,
					cpu.getProgramCounter(),
					cpu.getCache(),
					statement.getOriginalStatement().reconstruct()));
			switch (statement.getType()) {
				case EMPTY:
				case LABEL:
					cpu.updateProgramCounter();
					break;
				case JUMP:
					handleJump(labels, statement);
					break;

				case INSTRUCTION:
					handleInstruction(statement, labels, aliases);
					cpu.updateProgramCounter();
					break;

				default:
					throw new UnsupportedOperationException();
			}
			cpu.updateCycles();
		}
	}


	private Map<String, Integer> prefillLabels() {
		Map<String, Integer> labels = new HashMap<>();
		cpu.getProgram().getStatements().stream()
				.filter(statement -> statement.getType() == LABEL)
				.forEach(statement -> labels.put(statement.getChildren().get(0).getText().get(),
						statement.getOriginalStatement().getLine() + 1));

		return labels;
	}

	private void handleInstruction(ParsedStatement statement, Map<String, Integer> labels, Map<String, Integer> aliases) {
		var children = statement.getChildren();
		var instruction = children.get(0);
		switch (Literal.fromText(instruction.getText().get()).get()) {
			case ADD:
				handleAdd(statement, aliases);
				break;

			case ALIAS:
				handleAlias(statement, aliases);
				break;

			case BUMP_MIN:
				handleBumpMin(statement, aliases);
				break;

			case BUMP_PLUS:
				handleBumpPlus(statement, aliases);
				break;

			case COPYFROM:
				handleCopyfrom(statement, aliases);
				break;

			case COPYFROM_STAR:
				handleCopyfromStar(statement, aliases);
				break;

			case COPYTO:
				handleCopyto(statement, aliases);
				break;

			case COPYTO_STAR:
				handleCopytoStar(statement, aliases);
				break;

			case INBOX:
				handleInbox();
				break;

			case OUTBOX:
				handleOutbox();
				break;

			case SUB:
				handleSub(statement, aliases);
				break;

			default:
				throw new UnsupportedOperationException();
		}
	}

	private Optional<Value> getFirstParameter(ParsedStatement statement) {
		return getAllNonLiteralChildren(statement).stream()
				.findFirst();
	}

	private Optional<Value> getSecondParameter(ParsedStatement statement) {
		return getAllNonLiteralChildren(statement).stream()
				.skip(1)
				.findFirst();
	}

	private List<Value> getAllNonLiteralChildren(ParsedStatement statement) {
		return statement.getChildren().stream()
				.filter(value -> value.getType() != ValueType.LITERAL)
				.collect(Collectors.toList());
	}

	private void handleAdd(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = Integer.parseInt(cpu.getFromRegister(registerNumber));
		int cacheValue = Integer.parseInt(cpu.getCache());
		cpu.setCache("" + (value + cacheValue));
	}

	private void handleAlias(ParsedStatement statement, Map<String, Integer> aliases) {
		var alias = getFirstParameter(statement).get().getText().get();
		var registerNumber = Integer.parseInt(getSecondParameter(statement).get().getText().get());
		aliases.put(alias, registerNumber);
	}

	private void handleBumpMin(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = Integer.parseInt(cpu.getFromRegister(registerNumber)) - 1;
		cpu.setToRegister(registerNumber, "" + value);
		cpu.setCache("" + value);
	}

	private void handleBumpPlus(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = Integer.parseInt(cpu.getFromRegister(registerNumber)) + 1;
		cpu.setToRegister(registerNumber, "" + value);
		cpu.setCache("" + value);
	}

	private void handleCopyfrom(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = cpu.getFromRegister(registerNumber);
		cpu.setCache(value);
	}

	private void handleCopyfromStar(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = Integer.parseInt(cpu.getFromRegister(registerNumber));
		cpu.setCache(cpu.getFromRegister(value));
	}

	private void handleCopyto(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		cpu.setToRegister(registerNumber, cpu.getCache());
	}

	private void handleCopytoStar(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		var value = Integer.parseInt(cpu.getFromRegister(registerNumber));
		cpu.setToRegister(value, cpu.getCache());
	}

	private void handleInbox() {
		cpu.setCache(cpu.readInbox());
	}

	private void handleJump(Map<String, Integer> labels, ParsedStatement statement) {
		var jumpType = Literal.fromText(statement.getChildren().get(0).getText().get()).get();
		switch (jumpType) {
			case JUMP:
				handleUnconditionalJump(labels, statement);
				break;

			case JUMP0:
				handleJump0(labels, statement);
				break;

			case JUMPN:
				handleJumpn(labels, statement);
				break;
		}
	}

	private void handleUnconditionalJump(Map<String, Integer> labels, ParsedStatement statement) {
		String label = getLabel(statement);
		cpu.updateProgramCounter(labels.get(label));
	}

	private void handleJump0(Map<String, Integer> labels, ParsedStatement statement) {
		var cacheValue = cpu.getCache();
		if (cacheValue.equals("0")) {
			String label = getLabel(statement);
			cpu.updateProgramCounter(labels.get(label));
		} else {
			cpu.updateProgramCounter();
		}
	}

	private void handleJumpn(Map<String, Integer> labels, ParsedStatement statement) {
		try {
			var cacheValue = Integer.parseInt(cpu.getCache());
			if (cacheValue < 0) {
				String label = getLabel(statement);
				cpu.updateProgramCounter(labels.get(label));
			} else {
				cpu.updateProgramCounter();
			}
		} catch (NumberFormatException nfe) {
			cpu.updateProgramCounter();
		}
	}

	private void handleOutbox() {
		cpu.addToOutbox(cpu.getCache());
		cpu.setCache(null);
	}

	private void handleSub(ParsedStatement statement, Map<String, Integer> aliases) {
		var registerNumber = getRegisterNumber(statement, aliases);
		if (isSingleAlphabeticCharacterString(cpu.getCache())
			&& isSingleAlphabeticCharacterString(cpu.getFromRegister(registerNumber))) {
			var value = cpu.getFromRegister(registerNumber).charAt(0);
			var cacheValue = cpu.getCache().charAt(0);
			cpu.setCache("" + (cacheValue - value));
		} else {
			var value = Integer.parseInt(cpu.getFromRegister(registerNumber));
			int cacheValue = Integer.parseInt(cpu.getCache());
			cpu.setCache("" + (cacheValue - value));
		}
	}

	private ParsedStatement getStatement() {
		var program = cpu.getProgram();
		return program.getStatements().stream()
				.filter(statement -> statement.getOriginalStatement().getLine() == cpu.getProgramCounter())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("The program counter points to an instruction that doesn't exist!"));
	}

	private String getLabel(ParsedStatement statement) {
		return getFirstParameter(statement).get().getText().get();
	}

	private int getRegisterNumber(ParsedStatement statement, Map<String, Integer> aliases) {
		var firstParameter = getFirstParameter(statement).get();
		if (firstParameter.getType() == ValueType.ALIAS_REFERENCE) {
			return aliases.get(firstParameter.getText().get());
		}
		return Integer.parseInt(firstParameter.getText().get());
	}

	private boolean isSingleAlphabeticCharacterString(String s) {
		return s.length() == 1
				&& s.toUpperCase().charAt(0) >= 'A'
				&& s.toUpperCase().charAt(0) <= 'Z';
	}
}
