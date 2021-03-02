package nl.suriani.hrmasm.lib.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Program {
	private List<Statement> statements;

	public Program() {
		statements = new ArrayList<>();
	}

	public void addStatement(Statement statement) {
		statements.add(statement);
	}

	public List<Statement> getStatements() {
		return Collections.unmodifiableList(statements);
	}
}
