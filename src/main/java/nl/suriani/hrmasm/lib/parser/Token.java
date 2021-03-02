package nl.suriani.hrmasm.lib.parser;

public class Token {
	private String text;
	private Value value;

	public Token(String text, Value value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Token{" + "value=" + text + ", type=" + value + '}';
	}
}
