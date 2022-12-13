package nl.suriani.hrmasm.cpu;

public interface Indexes {
    static void checkCanFetch(int idx, int size) {
        if (isIndexOutOfBound(idx, size)) {
            throw new CannotFetchException();
        }
    }

    static void checkCanStore(int idx, int size) {
        if (isIndexOutOfBound(idx, size)) {
            throw new CannotStoreException();
        }
    }

    private static boolean isIndexOutOfBound(int idx, int size) {
        return idx < 0 || idx >= size;
    }
}
