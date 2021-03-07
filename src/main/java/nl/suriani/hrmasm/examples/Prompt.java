package nl.suriani.hrmasm.examples;

import nl.suriani.hrmasm.lib.CPU;
import nl.suriani.hrmasm.lib.Interpreter;
import nl.suriani.hrmasm.lib.parser.Lexer;
import nl.suriani.hrmasm.lib.parser.Parser;

import java.util.Objects;
import java.util.Scanner;

public class Prompt {
	public static void main(String[] args) {
		var source = new Scanner(Objects.requireNonNull(Prompt.class.getClassLoader().getResourceAsStream("prompt.asm")),
				"UTF-8")
				.useDelimiter("\\A")
				.next();

		var lexer = new Lexer();
		var parser = new Parser();
		var program = lexer.tokenize(source);
		var ast = parser.parse(program);
		var cpu = new CPU();
		cpu.setProgram(ast);

		cpu.addToInbox("quit");
		cpu.addToInbox("Hello");
		cpu.addToInbox("Hallo");
		cpu.addToInbox("Hoi");
		cpu.addToInbox("Hej");
		cpu.addToInbox("Hej do");

		var interpreter = new Interpreter(cpu);
		interpreter.run(ast);
	}
}
