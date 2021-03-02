package nl.suriani.hrmasm.lib.parser;

public class SyntaxError extends Error {
	public SyntaxError(String message) {
		super(message);
	}

	public SyntaxError(String message, Throwable cause) {
		super(message, cause);
	}
}
