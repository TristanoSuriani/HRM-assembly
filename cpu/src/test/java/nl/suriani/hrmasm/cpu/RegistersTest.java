package nl.suriani.hrmasm.cpu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistersTest {
    @Test
    void fetchAddressIsNegative() {
        Tester.givenAddressIs(-1)
                .whenRegisterIsFetched()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void fetchAddressIsEqualOrGreaterThanRegisterSize() {
        Tester.givenAddressIs(Registers.SIZE)
                .whenRegisterIsFetched()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void fetchAddressIsBetween0AndRegisterSize() {
        Tester.givenAddressIs(0)
                .whenRegisterIsFetched()
                .thenValueIsFetchedWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE / 2)
                .whenRegisterIsFetched()
                .thenValueIsFetchedWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE - 1)
                .whenRegisterIsFetched()
                .thenValueIsFetchedWithoutErrors();
    }

    @Test
    void storeAddressIsNegative() {
        Tester.givenAddressIs(-1)
                .whenAValueIsStored(10)
                .thenACannotStoreErrorOccurs();
    }

    @Test
    void storeAddressIsEqualOrGreaterThanRegisterSize() {
        Tester.givenAddressIs(Registers.SIZE)
                .whenAValueIsStored('t')
                .thenACannotStoreErrorOccurs();
    }

    @Test
    void storeValueAndFetch() {
        Tester.givenAddressIs(Registers.SIZE / 4)
                .whenAValueIsStoredAndThenFetched(10)
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs(10);

        Tester.givenAddressIs(Registers.SIZE / 4)
                .whenAValueIsStoredAndThenFetched('c')
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs('c');

        Tester.givenAddressIs(Registers.SIZE / 4)
                .whenAValueIsStoredAndThenFetched('c')
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs(99); // ascii value of 'c'

        Tester.givenAddressIs(Registers.SIZE / 4)
                .whenAValueIsStoredAndThenFetched(99) // ascii value of 'c'
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs('c');
    }

    @Test
    void storeAddressIsBetween0AndRegisterSize() {
        Tester.givenAddressIs(0)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(0)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE / 2)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE / 2)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE - 1)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Registers.SIZE - 1)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();
    }

    private static class Tester {
        private int idx;
        private Registers registers;

        static Tester givenAddressIs(int idx) {
            var tester = new Tester();
            tester.registers = new Registers();
            tester.idx = idx;
            return tester;
        }

        Asserter whenRegisterIsFetched() {
            var asserter = new Asserter();
            try {
                asserter.value = registers.fetch(idx);
            } catch (CannotFetchException cannotFetchException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStored(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                registers.store(idx, value);
                asserter.value = null;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotStore = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStored(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                registers.store(idx, value);
                asserter.value = null;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotStore = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStoredAndThenFetched(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                registers.store(idx, value);
                asserter.value = registers.fetch(idx);
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotStore = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStoredAndThenFetched(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                registers.store(idx, value);
                var fetchedValue = registers.fetch(idx);
                asserter.value = fetchedValue;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }
    }

    private static class Asserter {
        private Value value;
        private boolean cannotFetch;
        private boolean cannotStore;

        void thenACannotFetchErrorOccurs() {
            assertTrue(cannotFetch);
        }

        Asserter thenValueIsFetchedWithoutErrors() {
            assertFalse(cannotFetch);
            assertNotNull(value);
            return this;
        }

        void thenFetchedValueIs(int expectedValue) {
            assertEquals(expectedValue, value.asNumber());
        }

        void thenFetchedValueIs(char expectedValue) {
            assertEquals(expectedValue, value.asCharachter());
        }

        Asserter thenValueIsStoredWithoutErrors() {
            assertFalse(cannotStore);
            return this;
        }

        void thenACannotStoreErrorOccurs() {
            assertTrue(cannotStore);
        }
    }
}