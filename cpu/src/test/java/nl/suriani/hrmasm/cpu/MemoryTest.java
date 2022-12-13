package nl.suriani.hrmasm.cpu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {
    @Test
    void fetchAddressIsNegative() {
        Tester.givenAddressIs(-1)
                .whenMemoryIsFetched()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void fetchAddressIsEqualOrGreaterThanMemorySize() {
        Tester.givenAddressIs(Memory.SIZE)
                .whenMemoryIsFetched()
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void fetchAddressIsBetween0AndMemorySize() {
        Tester.givenAddressIs(0)
                .whenMemoryIsFetched()
                .thenValueIsFetchedWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE / 2)
                .whenMemoryIsFetched()
                .thenValueIsFetchedWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE - 1)
                .whenMemoryIsFetched()
                .thenValueIsFetchedWithoutErrors();
    }

    @Test
    void storeAddressIsNegative() {
        Tester.givenAddressIs(-1)
                .whenAValueIsStored(10)
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void storeAddressIsEqualOrGreaterThanMemorySize() {
        Tester.givenAddressIs(Memory.SIZE)
                .whenAValueIsStored('t')
                .thenACannotFetchErrorOccurs();
    }

    @Test
    void storeValueAndFetch() {
        Tester.givenAddressIs(Memory.SIZE / 4)
                .whenAValueIsStoredAndThenFetched(10)
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs(10);

        Tester.givenAddressIs(Memory.SIZE / 4)
                .whenAValueIsStoredAndThenFetched('c')
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs('c');

        Tester.givenAddressIs(Memory.SIZE / 4)
                .whenAValueIsStoredAndThenFetched('c')
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs(99); // ascii value of 'c'

        Tester.givenAddressIs(Memory.SIZE / 4)
                .whenAValueIsStoredAndThenFetched(99) // ascii value of 'c'
                .thenValueIsStoredWithoutErrors()
                .thenValueIsFetchedWithoutErrors()
                .thenFetchedValueIs('c');
    }

    @Test
    void storeAddressIsBetween0AndMemorySize() {
        Tester.givenAddressIs(0)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(0)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE / 2)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE / 2)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE - 1)
                .whenAValueIsStored(10)
                .thenValueIsStoredWithoutErrors();

        Tester.givenAddressIs(Memory.SIZE - 1)
                .whenAValueIsStored('c')
                .thenValueIsStoredWithoutErrors();
    }

    private static class Tester {
        private int address;
        private Memory memory;

        static Tester givenAddressIs(int address) {
            var tester = new Tester();
            tester.memory = new Memory();
            tester.address = address;
            return tester;
        }

        Asserter whenMemoryIsFetched() {
            var asserter = new Asserter();
            try {
                var value = memory.fetch(address);
                asserter.value = value;
            } catch (CannotFetchException cannotFetchException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStored(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                memory.store(address, value);
                asserter.value = null;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStored(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                memory.store(address, value);
                asserter.value = null;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStoredAndThenFetched(int intValue) {
            var asserter = new Asserter();
            var value = new Value(intValue);
            try {
                memory.store(address, value);
                var fetchedValue = memory.fetch(address);
                asserter.value = fetchedValue;
            } catch (CannotStoreException cannotStoreException) {
                asserter.cannotFetch = true;
            }
            return asserter;
        }

        Asserter whenAValueIsStoredAndThenFetched(char charValue) {
            var asserter = new Asserter();
            var value = new Value(charValue);
            try {
                memory.store(address, value);
                var fetchedValue = memory.fetch(address);
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