package nl.suriani.hrmasm.lib;

import lombok.SneakyThrows;
import nl.suriani.hrmasm.lib.parser.Lexer;
import nl.suriani.hrmasm.lib.parser.Literal;
import nl.suriani.hrmasm.lib.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HRMTest {

	public static final String SOURCE_DIRECTORY = "src/test/asm/nl/suriani/hrmasm/app/";
	private CPU cpu;
	private Interpreter interpreter;

	@BeforeEach
	void setUp() {
		cpu = new CPU(true);
		interpreter = new Interpreter(cpu);
	}

	@ParameterizedTest
	@MethodSource("sourceFiles")
	void test(String fileName) {
		String path = SOURCE_DIRECTORY + fileName;
		List<String> allLines = readFile(path);
		CurrentSection currentSection = null;
		List<String> setupLines = new ArrayList<>();
		List<String> programLines = new ArrayList<>();
		List<String> testLines = new ArrayList<>();

		for (String line: allLines) {
			if (line.trim().equals(".setup")) {
				currentSection = CurrentSection.SETUP;
			} else if (line.trim().equals(".program")) {
				currentSection = CurrentSection.PROGRAM;
			} else if (line.trim().equals(".test")) {
				currentSection = CurrentSection.TEST;
			} else {
				if (currentSection == null && !line.trim().startsWith(";") && !line.trim().isEmpty()) {
					throw new IllegalStateException("Instruction '" + line + "' is not in a section!");
				} else if (currentSection == CurrentSection.SETUP) {
					setupLines.add(line);
				} else if (currentSection == CurrentSection.PROGRAM) {
					programLines.add(line);
				} else if (currentSection == CurrentSection.TEST) {
					testLines.add(line);
				}
			}
		}
		prepareProgram(setupLines);
		try {
			executeProgram(programLines);
		} catch (NoSuchElementException noSuchElementException) {
			if (cpu.getInstruction().getChildren().get(0).getText().get().equals(Literal.INBOX.getText())) {
				System.out.println("Execution stopped. Inbox is empty.");
			} else {
				throw noSuchElementException;
			}
		}
		System.out.println("Total cycles: " + cpu.getCycles());
		testProgram(testLines);
	}

	private static Stream<Arguments> sourceFiles() {
		File directory = new File(SOURCE_DIRECTORY);
		return Stream.of(directory.list())
				.filter(fileName -> fileName.endsWith(".asm"))
				//.filter(fileName -> fileName.startsWith("hrm40"))
				.map(Arguments::of);
	}

	@SneakyThrows
	private static List<String> readFile(String path) {
		return Files.readAllLines(Paths.get(path));
	}

	private void prepareProgram(List<String> setupLines) {
		for(String line: setupLines) {
			String instruction = line.trim();
			if(line.trim().length() > 0) {
				String[] parts = instruction.split(" ");
				if (!line.contains(";")) {
					assertEquals(3, parts.length, "Syntax error -> " + instruction);
				}
				String instructionName = parts[0];
				String register = parts[1];
				String value = parts[2];
				if (register.equals("inbox")) {
					cpu.addToInbox(value);
				} else if (register.toUpperCase().startsWith("R")) {
					int registerNumber = Integer.parseInt(register.substring(1));
					cpu.setToRegister(registerNumber, value);
				}
			}
		}
	}

	private void executeProgram(List<String> programLines) {
		var source = String.join("\n", programLines);
		var lexer = new Lexer();
		var parser = new Parser();
		var program = parser.parse(lexer.tokenize(source));
		cpu.setProgram(program);
		interpreter.run(program);
	}

	private void testProgram(List<String> testLines) {
		for(String line: testLines) {
			String instruction = line.trim();
			if (instruction.length() > 0) {
				String[] parts = instruction.split(" ");
				Deque<String> expectedOutbox = new ArrayDeque<>(Arrays.asList(parts).subList(2, parts.length));
				assertEquals(expectedOutbox.size(), cpu.getOutboxSize(),
						"The outbox has a different size than expected. ==>\n"
					+ cpu.printOutbox());
				int outboxSize = cpu.getOutboxSize();
				for (int i = 0; i < outboxSize; i += 1) {
					String actual = cpu.readOutbox();
					String expected = expectedOutbox.pop();
					assertEquals(expected, actual,
							"[Assertion #" + i + "] Incorrect value in outbox.");
				}
				assertTrue(expectedOutbox.isEmpty(), "The test stopped before the outbox has been emptied for inspection.");
				return;
			}
		}
	}
}
