package de.ktbl.android.sharedlibrary.annotation.model;

public interface DefaultValueProvider<T> {
    T getDefaultValue(Long id);
}
