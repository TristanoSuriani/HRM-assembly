package nl.suriani.hrmasm.program;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Instruction(StatementType type, List<String> params) {
    public Instruction {
        Objects.requireNonNull(type);
        Objects.requireNonNull(params);
    }

    public static Instruction statement(StatementType statementType, String... params) {
        return new Instruction(statementType, Arrays.asList(params));
    }
}
