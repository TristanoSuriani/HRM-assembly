package nl.suriani.hrmasm.lib.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ParserRule {
	private List<Value> children;
	private ParserRuleType type;

	public ParserRule(ParserRuleType type, Value... children) {
		this.type = type;
		this.children = Arrays.asList(children);
	}

	public ParserRule(List<Value> children) {
		this.children = Collections.unmodifiableList(children);
	}

	public boolean isSatisfiedBy(Statement statement) {
		return children.size() == statement.getTokens().size()
				&& allTheTypesMatch(statement);
	}

	private boolean allTheTypesMatch(Statement statement) {
		return Stream.iterate(0, n -> n + 1)
				.limit(children.size())
				.allMatch(i -> children.get(i).equals(statement.getTokens().get(i).getValue()));
	}

	public List<Value> getChildren() {
		return new ArrayList<>(children);
	}

	public ParserRuleType getType() {
		return type;
	}
}
