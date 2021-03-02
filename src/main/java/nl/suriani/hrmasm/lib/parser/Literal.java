package nl.suriani.hrmasm.lib.parser;

import java.util.Arrays;
import java.util.Optional;

public enum Literal {

	ALIAS("alias"),
	COLON(":"),
	COMMA(","),
	ADD("add"),
	DIV("div"),
	EQ("eq"),
	MUL("mul"),
	SUB("sub"),
	BUMP_PLUS("bump+"),
	BUMP_MIN("bump-"),
	DB("db"),
	COPYFROM("copyfrom"),
	COPYTO("copyto"),
	COPYFROM_STAR("copyfrom*"),
	COPYTO_STAR("copyto*"),
	JUMP("jump"),
	JUMP0("jump0"),
	JUMPN("jumpn"),
	MOV("mov"),
	INBOX("inbox"),
	OUTBOX("outbox"),
	RET("ret"),
	RETV("retv"),
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
