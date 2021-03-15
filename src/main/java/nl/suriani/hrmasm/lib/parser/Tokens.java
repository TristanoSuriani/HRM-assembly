package nl.suriani.hrmasm.lib.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tokens {
	private List<Statement> statements;

	public Tokens() {
		statements = new ArrayList<>();
	}

	public void addStatement(Statement statement) {
		statements.add(statement);
	}

	public List<Statement> getStatements() {
		return Collections.unmodifiableList(statements);
	}
}
