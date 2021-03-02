package nl.suriani.hrmasm.lib.parser;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

@ToString
@EqualsAndHashCode
public class Value {
	private String text;
	private ValueType type;

	public Value(String text, ValueType type) {
		Objects.requireNonNull(type);
		this.type = type;
		this.text = text;
	}

	public Value(ValueType type) {
		Objects.requireNonNull(type);
		this.type = type;
	}

	public Optional<String> getText() {
		return Optional.ofNullable(text);
	}

	public ValueType getType() {
		return type;
	}

	public static Value literal(Literal literal) {
		return new Value(literal.getText(), ValueType.LITERAL);
	}

	public static Value type(ValueType type) {
		return new Value(type);
	}
}

