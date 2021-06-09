package de.ktbl.android.sharedlibrary.livedata;

import androidx.lifecycle.MediatorLiveData;

/**
 * Extends MediatorLiveData and overrides setValue and postValue
 * The overwritten methods check whether the new value is really new.
 * If new value equals the existing value it is not set again; else
 * the default implementations of the setValue / postValue are called
 *
 * @param <T>
 * @see MediatorLiveData
 */
public class BindingSafeMediatorLiveData<T> extends MediatorLiveData<T> {

    @Override
    public void setValue(T newValue) {
        if (newValue == null) {
            if (this.getValue() != null) {
                super.setValue(newValue);
            }
        } else if (!newValue.equals(this.getValue())) {
            super.setValue(newValue);
        }
    }

    @Override
    public void postValue(T newValue) {
        if (newValue == null) {
            if (this.getValue() != null) {
                super.postValue(newValue);
            }
        } else if (!newValue.equals(this.getValue())) {
            super.postValue(newValue);
        }
    }
}
