package de.ktbl.android.sharedlibrary.util;

import de.ktbl.android.sharedlibrary.BuildConfig;

/**
 * Util class providing functionality to ease pure debugging
 * calls on Android. It utilizes {@link BuildConfig}.
 * <p>
 * TODO: Currently it only utilizes the Buildconfig of the SharedLibrary.
 * These function should also have an overload which has an additional
 * parameter of type {@code BuildConfig}, thus the caller can use it's
 * own BuildConfig independent of the SharedLibrary's build settings.
 */
public class IfDebug {
    private IfDebug() {
        // Static class
    }

    /**
     * @param ifDebug returned if {@link BuildConfig#DEBUG} is set
     * @param orElse  returned if {@link BuildConfig#DEBUG} is <b>not</b> set
     * @return Returns either of the string parameters if the {@link BuildConfig} is set to {@link BuildConfig#DEBUG}
     */
    public static String ifDebugElse(String ifDebug, String orElse) {
        if (BuildConfig.DEBUG) {
            return ifDebug;
        } else {
            return orElse;
        }
    }

    /**
     * @param ifDebug returned if {@link BuildConfig#DEBUG} is set
     * @param orElse  returned if {@link BuildConfig#DEBUG} is <b>not</b> set
     * @return Returns either of the parameters if the {@link BuildConfig} is set
     * to {@link BuildConfig#DEBUG}
     */
    public static <T> T ifDebugElse(T ifDebug, T orElse) {
        if (BuildConfig.DEBUG) {
            return ifDebug;
        } else {
            return orElse;
        }
    }

    /**
     * Executes either of the functions given as parameter if the {@link BuildConfig} is set
     * to {@link BuildConfig#DEBUG}
     * @param execIfDebug executed if {@link BuildConfig#DEBUG} is set
     * @param execElse executed if {@link BuildConfig#DEBUG} is <b>not</b> set
     */
    public static void ifDebugElse(VoidFunction execIfDebug, VoidFunction execElse) {
        if (BuildConfig.DEBUG) {
            if (execIfDebug != null) {
                execIfDebug.exec();
            }
        } else {
            if (execElse != null) {
                execElse.exec();
            }
        }
    }
}
