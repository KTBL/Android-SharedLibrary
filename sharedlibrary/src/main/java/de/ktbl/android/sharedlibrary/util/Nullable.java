package de.ktbl.android.sharedlibrary.util;

/**
 * This Class is provided in here since we support Level 23 Min API and the Optional of java is
 * available at Min Leven 24 API. This sucks, so we simply write our own optional.
 *
 * @param <T>
 */
public class Nullable<T> {
    private T value;

    public Nullable(T val) {
        this.value = val;
    }

    public static <T> Nullable<T> empty() {
        return new Nullable<>(null);
    }

    public static <T> Nullable<T> of(T value) {
        return new Nullable<>(value);
    }

    public T get() {
        return this.value;
    }

    public T getOrDefault(T defaultValue) {
        if (this.isNull()) {
            return defaultValue;
        } else {
            return this.value;
        }
    }

    public boolean isNotNull() {
        return !this.isNull();
    }

    public boolean isNull() {
        return this.value == null;
    }

    public void set(T value) {
        this.value = value;
    }
}
