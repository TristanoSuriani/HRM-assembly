package nl.suriani.hrmasm.lib.parser;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class AST {
	private List<ParsedStatement> statements;

	public AST() {
		this.statements = new ArrayList<>();
	}

	public void add(ParsedStatement statement) {
		statements.add(statement);
	}

	public List<ParsedStatement> getStatements() {
		return new ArrayList<>(statements);
	}
}
