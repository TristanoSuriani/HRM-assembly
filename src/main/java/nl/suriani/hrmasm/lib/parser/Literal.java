package nl.suriani.hrmasm.lib.parser;

import java.util.Arrays;
import java.util.Optional;

public enum Literal {
	ADD("add"),
	ALIAS("alias"),
	BUMP_MIN("bump-"),
	BUMP_PLUS("bump+"),
	COLON(":"),
	COMMA(","),
	COPYFROM("copyfrom"),
	COPYFROM_STAR("copyfrom*"),
	COPYTO("copyto"),
	COPYTO_STAR("copyto*"),
	DB("db"),
	DIV("div"),
	EQ("eq"),
	INBOX("inbox"),
	JUMP("jump"),
	JUMP0("jump0"),
	JUMPN("jumpn"),
	MOV("mov"),
	MUL("mul"),
	OUTBOX("outbox"),
	RDI("rdi"),
	RET("ret"),
	RETV("retv"),
	SUB("sub"),
	SYSCALL("syscall");

	public final String text;

	Literal(String text) {
		this.text = text;
	}

	public static Optional<Literal> fromText(String text) {
		return Arrays.stream(values())
				.filter(literal -> literal.text.equals(text))
				.findFirst();
	}

	public static boolean isLiteral(String text) {
		return fromText(text).isPresent();
	}

	public String getText() {
		return text;
	}
}
