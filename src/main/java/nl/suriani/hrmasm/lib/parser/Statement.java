package nl.suriani.hrmasm.lib.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statement {
	private int line;
	private List<Token> tokens;

	public Statement(int line) {
		this.tokens = new ArrayList<>();
		this.line = line;
	}

	public String reconstruct() {
		String text = "";
		for (Token token: tokens) {
			text += " ";
			text += token.getText();
		}
		return text;
	}

	public void addToken(Token token) {
		tokens.add(token);
	}

	public List<Token> getTokens() {
		return Collections.unmodifiableList(tokens);
	}

	public void replaceLastToken(Token newToken) {
		tokens.set(tokens.size() -1, newToken);
	}

	public int getLine() {
		return line;
	}

	@Override
	public String toString() {
		return "Statement{" + "line=" + line + ", tokens=" + tokens + '}';
	}
}
