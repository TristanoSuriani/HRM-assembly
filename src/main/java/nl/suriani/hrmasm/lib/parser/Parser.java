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
		var addIntegerDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ADD), type(INTEGER));
		var addIntegerWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ADD), type(ALIAS_REFERENCE));
		var addCharDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(ADD), type(CHAR));
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
		var eqDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(EQ), type(ALIAS_REFERENCE));
		var dbDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(DB), type(STRING));
		var emptyLineDefinition = new ParserRule(ParserRuleType.EMPTY);
		var inboxDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(INBOX));
		var jumpDefinition = new ParserRule(ParserRuleType.JUMP, literal(JUMP), type(ID));
		var jump0Definition = new ParserRule(ParserRuleType.JUMP, literal(JUMP0), type(ID));
		var jumpnDefinition = new ParserRule(ParserRuleType.JUMP, literal(JUMPN), type(ID));
		var labelDefinition = new ParserRule(ParserRuleType.LABEL, type(ID), literal(Literal.COLON));
		var labeledConstantDefinition = new ParserRule(ParserRuleType.CONSTANT_DEFINITION, type(ID), literal(Literal.COLON), literal(DB), type(STRING));
		var movDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(MOV), literal(RDI), literal(COMMA), type(ID));
		var movWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(MOV), literal(RDI), literal(COMMA), type(ALIAS_REFERENCE));
		var outboxDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(OUTBOX));
		var retDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(RET));
		var retvDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(RETV));
		var subDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(SUB) , type(INTEGER));
		var subWithAliasDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(SUB) , type(ALIAS_REFERENCE));
		var syscallDefinition = new ParserRule(ParserRuleType.INSTRUCTION, literal(SYSCALL) , type(ID));

		parserRules = Arrays.asList(
				labelDefinition,
				aliasDefinition,
				addCharDefinition,
				addIntegerDefinition,
				addIntegerWithAliasDefinition,
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
				dbDefinition,
				emptyLineDefinition,
				eqDefinition,
				jumpDefinition,
				jump0Definition,
				jumpnDefinition,
				inboxDefinition,
				labeledConstantDefinition,
				movDefinition,
				movWithAliasDefinition,
				outboxDefinition,
				retDefinition,
				retvDefinition,
				subDefinition,
				subWithAliasDefinition,
				syscallDefinition
		);
	}

	public AST parse(Program program) {
		var parsedProgram = new AST();
		for (Statement statement : program.getStatements()) {
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
