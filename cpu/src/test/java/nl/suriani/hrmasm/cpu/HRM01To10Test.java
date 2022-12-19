package nl.suriani.hrmasm.cpu;

import org.junit.jupiter.api.Test;

import static nl.suriani.hrmasm.program.Instruction.statement;
import static nl.suriani.hrmasm.program.StatementType.*;
import static nl.suriani.hrmasm.program.StatementType.JUMP;

public class HRM01To10Test extends CPUTest {
    @Test
    void noProgramLoaded() {
        givenNumbersAreAddedToInbox(1, 2, 3);
        whenProgramIsExecuted();
        thenOutboxContainsNoValues();
    }

    @Test
    void infiniteLoopDoesntBlockTheMachineForever() {
        givenCpuIsLoadedWithProgram(statement(JUMP, "0"));
        whenProgramIsExecuted();
        thenCPUIsHaltedAbnormally();
    }

    @Test
    void hrm01() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(OUTBOX),
                statement(INBOX),
                statement(OUTBOX),
                statement(INBOX),
                statement(OUTBOX)
        );

        givenNumbersAreAddedToInbox(1, 2, 3);
        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsNumbers(1, 2, 3);
    }

    @Test
    void hrm02() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(OUTBOX),
                statement(JUMP, "0")
        );

        givenCharachtersAreAddedToInbox('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsCharacters('A', 'U', 'T', 'O', 'E', 'X', 'E', 'C');
    }

    @Test
    void hrm03() {
        givenCpuIsLoadedWithProgram(
                statement(COPY_FROM, "4"),
                statement(OUTBOX),
                statement(COPY_FROM, "0"),
                statement(OUTBOX),
                statement(COPY_FROM, "3"),
                statement(OUTBOX)
        );

        givenNumbersAreAddedToInbox(-99, -99, -99, -99);
        givenCharachterIsPushedIntoRegister(0, 'U');
        givenCharachterIsPushedIntoRegister(1, 'J');
        givenCharachterIsPushedIntoRegister(2, 'X');
        givenCharachterIsPushedIntoRegister(3, 'G');
        givenCharachterIsPushedIntoRegister(4, 'B');
        givenCharachterIsPushedIntoRegister(5, 'E');

        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsCharacters('B', 'U', 'G');
    }

    @Test
    void hrm04() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(COPY_TO, "0"),
                statement(INBOX),
                statement(OUTBOX),
                statement(COPY_FROM, "0"),
                statement(OUTBOX),
                statement(JUMP, "0")
        );

        givenCharachtersAreAddedToInbox('4', '9', 'F', 'A', '2', '7');

        whenProgramIsExecuted();
        thenCPUIsHalted();
        thenOutboxContainsCharacters('9', '4', 'A', 'F', '7', '2');
    }

    @Test
    void hrm06() {
        givenCpuIsLoadedWithProgram(
                statement(INBOX),
                statement(COPY_TO, "0"),
                statement(INBOX),
                statement(ADD, "0"),
                statement(OUTBOX),
                statement(JUMP, "0")
        );

        givenNumbersAreAddedToInbox(4, 7, 3, 1, -1, 2, 6, -1);

        whenProgramIsExecutedWithDebugOutput();
        thenCPUIsHalted();
        thenOutboxContainsNumbers(11, 4, 1, 5);
    }
}
