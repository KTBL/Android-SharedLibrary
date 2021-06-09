package de.ktbl.android.sharedlibrary.livedata;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * Simple EventHandler which can safely be used to notify {@link Lifecycle} bound objects out of a
 * {@link ViewModel}
 * This is basically a reuse of the {@link LiveData} class.
 * @param <T>
 */
public class LiveEventHandler<T> extends LiveData<T> {

    /**
     * This is the equivalent of the method {@link LiveData#postValue(Object)} and is used to
     * post an event notification to the main thread.
     * @param value the event to notify all observers about
     */
    public void postNotifyLiveEvent(T value) {
        this.postValue(value);
    }

    /**
     * This is the equivalent of the method {@link LiveData#setValue(Object)} and is used to
     * notify observers about an event.
     * @param value the event to notify all observers about
     */
    public void notifyLiveEvent(T value) {
        this.setValue(value);
    }
}