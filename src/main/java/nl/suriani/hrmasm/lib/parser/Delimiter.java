package nl.suriani.hrmasm.lib.parser;

import java.util.Arrays;

public enum Delimiter {
	COMMA(","),
	COLON(":");

	private String text;

	Delimiter(String text) {
		this.text = text;
	}

	public static boolean isDelimiter(String token) {
		return Arrays.stream(values())
				.anyMatch(delimiter -> delimiter.text.equals(token));
	}

	public String getText() {
		return text;
	}
}
