package nl.suriani.hrmasm.lib.parser;

import java.util.Optional;
import java.util.regex.Pattern;

public enum ValueType {
	ALIAS_REFERENCE,
	CHAR,
	ID,
	INTEGER,
	LITERAL,
	STRING,
	UNRECOGNIZED;

	public static Optional<ValueType> fromText(String text) {
		if (text.startsWith("\"") && text.endsWith("\"")) {
			return Optional.of(STRING);
		}

		if (text.startsWith("#")) {
			return Optional.of(ALIAS_REFERENCE);
		}

		try {
			Integer.parseInt(text);
			return Optional.of(INTEGER);
		} catch (NumberFormatException nfe) {
			// no op
		}

		if (text.length() == 1) {
			return Optional.of(CHAR);
		}

		var pattern = Pattern.compile("^[a-zA-Z0-9\\-_]*$");
		var matcher = pattern.matcher(text);
		if (matcher.matches()) {
			return Optional.of(ID);
		} else {
			return Optional.empty();
		}
	}
}
