package nl.suriani.hrmasm.lib.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ParsedStatement {
	private Statement originalStatement;
	private ParserRuleType type;
	private List<Value> children;

	public List<Value> getChildren() {
		return new ArrayList<>(children);
	}
}
