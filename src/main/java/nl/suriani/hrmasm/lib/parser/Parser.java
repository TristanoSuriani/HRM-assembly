package nl.suriani.hrmasm.lib.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static nl.suriani.hrmasm.lib.parser.Literal.*;
import static nl.suriani.hrmasm.lib.parser.ValueType.*;
import static nl.suriani.hrmasm.lib.parser.Value.*;

public class Parser {
	private final List<ParserRule> parserRules;

	public Parser() {
		var aliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ALIAS), type(ALIAS_REFERENCE), type(INTEGER));
		var addDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ADD), type(INTEGER));
		var addWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ADD), type(ALIAS_REFERENCE));
		var bumpMinDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(BUMP_MIN), type(INTEGER));
		var bumpMinWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(BUMP_MIN), type(ALIAS_REFERENCE));
		var bumpPlusDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(BUMP_PLUS), type(INTEGER));
		var bumpPlusWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(BUMP_PLUS), type(ALIAS_REFERENCE));
		var copyfromDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYFROM), type(INTEGER));
		var copyfromStarDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYFROM_STAR), type(INTEGER));
		var copyfromStarWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYFROM_STAR), type(ALIAS_REFERENCE));
		var copyfromWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYFROM), type(ALIAS_REFERENCE));
		var copytoDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYTO), type(INTEGER));
		var copytoStarDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYTO_STAR), type(INTEGER));
		var copytoStarWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYTO_STAR), type(ALIAS_REFERENCE));
		var copytoWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(COPYTO), type(ALIAS_REFERENCE));
		var emptyLineDefinition = new ParserRule(ParserRuleType.EMPTY);
		var inboxDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(INBOX));
		var jumpDefinition = new ParserRule(ParserRuleType.JUMP, literal(JUMP), type(ID));
		var jump0Definition = new ParserRule(ParserRuleType.JUMP, literal(JUMP0), type(ID));
		var jumpnDefinition = new ParserRule(ParserRuleType.JUMP, literal(JUMPN), type(ID));
		var labelDefinition = new ParserRule(ParserRuleType.LABEL, type(ID), literal(Literal.COLON));
		var outboxDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(OUTBOX));
		var subDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(SUB) , type(INTEGER));
		var subWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(SUB) , type(ALIAS_REFERENCE));

		parserRules = Arrays.asList(
				labelDefinition,
				aliasDefinition,
				addDefinition,
				addWithAliasDefinition,
				bumpMinDefinition,
				bumpMinWithAliasDefinition,
				bumpPlusDefinition,
				bumpPlusWithAliasDefinition,
				copyfromDefinition,
				copyfromStarDefinition,
				copyfromStarWithAliasDefinition,
				copyfromWithAliasDefinition,
				copytoDefinition,
				copytoWithAliasDefinition,
				copytoStarDefinition,
				copytoStarWithAliasDefinition,
				emptyLineDefinition,
				jumpDefinition,
				jump0Definition,
				jumpnDefinition,
				inboxDefinition,
				outboxDefinition,
				subDefinition,
				subWithAliasDefinition
		);
	}

	public AST parse(Tokens tokens) {
		var parsedProgram = new AST();
		for (Statement statement : tokens.getStatements()) {
			var parserRule = parserRules.stream()
					.filter(rule -> rule.isSatisfiedBy(statement))
					.map(rule -> parseStatement(statement, rule))
					.findFirst()
					.orElseThrow(() -> new SyntaxError("Cannot parse line " + statement.getLine()
							+ ": " + statement.reconstruct()));
			parsedProgram.add(parserRule);
		}
		return parsedProgram;
	}

	private ParsedStatement parseStatement(Statement statement, ParserRule parserRule) {
		var children = Stream.iterate(0, i -> i < statement.getTokens().size(), i -> i + 1)
				.map(i -> new Value(statement.getTokens().get(i).getText(),
						statement.getTokens().get(i).getValue().getType()))
				.collect(Collectors.toList());
		return new ParsedStatement(statement, parserRule.getType(), children);
	}
}
