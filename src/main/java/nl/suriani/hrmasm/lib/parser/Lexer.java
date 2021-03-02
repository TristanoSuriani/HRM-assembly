package nl.suriani.hrmasm.lib.parser;

import java.util.Optional;

public class Lexer {
	public Program tokenize(String source) {
		final Program program = new Program();
		int lineCounter = 0;
		Statement currentStatement = new Statement(lineCounter);
		String currentSelection = newToken();
		boolean ignoreTheRestOfTheLine = false;
		boolean insideString = false;

		for(char c: source.toCharArray()) {
			if (isNewLineCharacter(c)) {
				ignoreTheRestOfTheLine = false;
				currentSelection = pushCurrentSelectionAndClear(currentStatement, currentSelection);
				currentStatement = pushCurrentStatementAndClear(program, currentStatement);
				continue;
			} else if (isCommentCharacter(c)) {
				ignoreTheRestOfTheLine = true;
				continue;
			}

			if (ignoreTheRestOfTheLine) {
				continue;
			}

			if (isDoubleQuotesCharacter(c)) {
				currentSelection += c;
				insideString = !insideString;
				continue;
			}

			if (insideString) {
				currentSelection += c;
				continue;
			}

			if (canIgnore(c)) {
				currentSelection = pushCurrentSelectionAndClear(currentStatement, currentSelection);
			} else if (delimitsTokenButCannotIgnore(c)) {
				currentSelection = pushCurrentSelectionAndClear(currentStatement, currentSelection);
				currentSelection += c;
				currentSelection = pushCurrentSelectionAndClear(currentStatement, currentSelection);
			} else  {
				currentSelection += c;
			}
		}

		return program;
	}

	private String pushCurrentSelectionAndClear(Statement statement, String currentSelection) {
		if (!currentSelection.isEmpty()) {
			makeToken(currentSelection)
					.ifPresent(statement::addToken);
			currentSelection = newToken();
		}
		return currentSelection;
	}

	private Statement pushCurrentStatementAndClear(Program program, Statement statement) {
		program.addStatement(statement);
		return new Statement(statement.getLine() + 1);
	}


	private String newToken() {
		return "";
	}
	
	private Optional<Token> makeToken(String text) {
		return Literal.fromText(text)
				.map(Value::literal)
				.map(value -> new Token(text, value))
				.or(() -> ValueType.fromText(text)
						.map(Value::type)
						.map(value -> new Token(text, value)));
	}

	private boolean canIgnore(char c) {
		return isBlankCharacter(c)
			|| isTabCharacter(c)
			|| isCarriageReturnCharacter(c)
			|| isNewLineCharacter(c)
			|| isEndOfFileCharacter(c);
	}

	private boolean delimitsTokenButCannotIgnore(char c) {
		return Delimiter.isDelimiter("" + c);
	}

	private boolean isCommentCharacter(char c) {
		return c == ';';
	}

	private boolean isCarriageReturnCharacter(char c) {
		return c == '\r';
	}

	private boolean isTabCharacter(char c) {
		return c == '\t';
	}

	private boolean isBlankCharacter(char c) {
		return c == ' ';
	}

	private boolean isEndOfFileCharacter(char c) {
		return c == '\0';
	}

	private boolean isNewLineCharacter(char c) {
		return c == '\n';
	}

	private boolean isDoubleQuotesCharacter(char c) {
		return c == '"';
	}
}
