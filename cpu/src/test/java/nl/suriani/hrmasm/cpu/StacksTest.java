package nl.suriani.hrmasm.cpu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StacksTest {
    @Test
    void popIndexIsNegative() {
        Tester.givenIndexIs(-1)
                .whenAValueIsPopped()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void popIndexIsEqualOrGreaterThanStacksSize() {
        Tester.givenIndexIs(Stacks.SIZE)
                .whenAValueIsPopped()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void popIndexIsBetween0AndStacksSize() {
        Tester.givenIndexIs(0)
                .whenAValueIsPopped()
                .thenValueIsPoppedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE / 2)
                .whenAValueIsPopped()
                .thenValueIsPoppedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE - 1)
                .whenAValueIsPopped()
                .thenValueIsPoppedWithoutErrors();
    }

    @Test
    void pushIndexIsNegative() {
        Tester.givenIndexIs(-1)
                .whenAValueIsPushed(10)
                .thenACannotPushErrorOccurs();
    }

    @Test
    void pushIndexIsEqualOrGreaterThanStacksSize() {
        Tester.givenIndexIs(Stacks.SIZE)
                .whenAValueIsPushed('t')
                .thenACannotPushErrorOccurs();
    }

    @Test
    void pushValueAndFetch() {
        Tester.givenIndexIs(Stacks.SIZE / 4)
                .whenAValueIsPushedAndThenPopped(10)
                .thenValueIsPushedWithoutErrors()
                .thenValueIsPoppedWithoutErrors()
                .thenPoppedValueIs(10);

        Tester.givenIndexIs(Stacks.SIZE / 4)
                .whenAValueIsPushedAndThenPopped('c')
                .thenValueIsPushedWithoutErrors()
                .thenValueIsPoppedWithoutErrors()
                .thenPoppedValueIs('c');

        Tester.givenIndexIs(Stacks.SIZE / 4)
                .whenAValueIsPushedAndThenPopped('c')
                .thenValueIsPushedWithoutErrors()
                .thenValueIsPoppedWithoutErrors()
                .thenPoppedValueIs(99); // ascii value of 'c'

        Tester.givenIndexIs(Stacks.SIZE / 4)
                .whenAValueIsPushedAndThenPopped(99) // ascii value of 'c'
                .thenValueIsPushedWithoutErrors()
                .thenValueIsPoppedWithoutErrors()
                .thenPoppedValueIs('c');
    }

    @Test
    void pushIndexIsBetween0AndStacksSize() {
        Tester.givenIndexIs(0)
                .whenAValueIsPushed(10)
                .thenValueIsPushedWithoutErrors();

        Tester.givenIndexIs(0)
                .whenAValueIsPushed('c')
                .thenValueIsPushedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE / 2)
                .whenAValueIsPushed(10)
                .thenValueIsPushedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE / 2)
                .whenAValueIsPushed('c')
                .thenValueIsPushedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE - 1)
                .whenAValueIsPushed(10)
                .thenValueIsPushedWithoutErrors();

        Tester.givenIndexIs(Stacks.SIZE - 1)
                .whenAValueIsPushed('c')
                .thenValueIsPushedWithoutErrors();
    }

    private static class Tester {
        private int address;
        private Stacks stacks;

        static Tester givenIndexIs(int address) {
            var tester = new Tester();
            tester.stacks = new Stacks();
            tester.address = address;
            return tester;
        }

        Asserter whenAValueIsPopped() {
            var asserter = new Asserter();
            try {
                asserter.value = stacks.pop(address).orElse(null);
            } catch (CannotFetchException cannotFetchException) {
                asserter.cannotPop = true;
            }
            return asserter;
        }

        Asserter whenAValueIsPushed(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                stacks.push(address, value);
                asserter.value = null;
            } catch (CannotStoreException cannotFetchException) {
                asserter.cannotPush = true;
            }
            return asserter;
        }

        Asserter whenAValueIsPushed(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                stacks.push(address, value);
                asserter.value = null;
            } catch (CannotStoreException cannotFetchException) {
                asserter.cannotPush = true;
            }
            return asserter;
        }

        Asserter whenAValueIsPushedAndThenPopped(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                stacks.push(address, value);
                asserter.value = stacks.pop(address).orElse(null);
            } catch (CannotStoreException cannotFetchException) {
                asserter.cannotPop = true;
            }
            return asserter;
        }

        Asserter whenAValueIsPushedAndThenPopped(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                stacks.push(address, value);
                asserter.value = stacks.pop(address).orElse(null);
            } catch (CannotFetchException cannotFetchException) {
                asserter.cannotPop = true;
            }
            return asserter;
        }
    }

    private static class Asserter {
        private Value value;
        private boolean cannotPush;
        private boolean cannotPop;

        void thenACannotFetchErrorOccurs() {
            assertTrue(cannotPop);
        }

        Asserter thenValueIsPoppedWithoutErrors() {
            assertFalse(cannotPop);
            return this;
        }

        void thenPoppedValueIs(int expectedValue) {
            assertEquals(expectedValue, value.asNumber());
        }

        void thenPoppedValueIs(char expectedValue) {
            assertEquals(expectedValue, value.asCharachter());
        }

        Asserter thenValueIsPushedWithoutErrors() {
            assertFalse(cannotPush);
            return this;
        }

        void thenACannotPushErrorOccurs() {
            assertTrue(cannotPush);
        }
    }
}