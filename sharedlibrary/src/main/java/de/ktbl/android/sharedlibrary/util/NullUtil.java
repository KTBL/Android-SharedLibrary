package de.ktbl.android.sharedlibrary.util;

public class NullUtil {
    private NullUtil() {
        // http://www.commitstrip.com/en/2019/01/28/git-lfs/
    }

    public static <T> void ifNotNull(T nullable, Command ifNotNull) {
        if (nullable != null) {
            ifNotNull.doIt();
        }
    }

    public static <T> void ifNull(T nullable, Command ifNull) {
        if (nullable == null) {
            ifNull.doIt();
        }
    }
}
