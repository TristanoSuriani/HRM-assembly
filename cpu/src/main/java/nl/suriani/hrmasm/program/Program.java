package nl.suriani.hrmasm.program;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Program(List<Instruction> instructions) {
    public Program {
        Objects.requireNonNull(instructions);
    }

    public static Program program(Instruction... instructions) {
        return new Program(Arrays.asList(instructions));
    }
}
