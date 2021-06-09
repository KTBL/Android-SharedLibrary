package de.ktbl.android.sharedlibrary.view.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import de.ktbl.android.sharedlibrary.SnackbarNotificationEvent;

public interface ISnackbarNotificationRequester {
    /**
     * @see androidx.lifecycle.LiveData#observe(LifecycleOwner, Observer)
     */
    void observeSnackbarNotificationRequests(@NonNull LifecycleOwner owner,
                                             @NonNull Observer<SnackbarNotificationEvent> observer);

    /**
     * @see androidx.lifecycle.LiveData#removeObservers(LifecycleOwner)
     */
    void removeSnackbarNotificationObservers(@NonNull LifecycleOwner owner);
}
