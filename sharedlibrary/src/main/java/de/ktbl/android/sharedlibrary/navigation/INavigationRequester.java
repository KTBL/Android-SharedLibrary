package de.ktbl.android.sharedlibrary.navigation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import de.ktbl.android.sharedlibrary.livedata.LiveEventHandler;
import de.ktbl.android.sharedlibrary.navigation.NavigationCommand;

/**
 * Interface to be used in {@link androidx.lifecycle.ViewModel} classes if they want to
 * navigate to other views. This is done via a {@link LiveEventHandler}.
 */
public interface INavigationRequester {
    /**
     * @see androidx.lifecycle.LiveData#observe(LifecycleOwner, Observer)
     */
    void observeNavigationRequests(@NonNull LifecycleOwner owner, @NonNull Observer<NavigationCommand> observer);

    /**
     * @see androidx.lifecycle.LiveData#observeForever(Observer)
     */
    void observeNavigationRequestsForever(@NonNull Observer<NavigationCommand> observer);

    /**
     * @see androidx.lifecycle.LiveData#removeObserver(Observer)
     */
    void removeNavigationObserver(@NonNull Observer<NavigationCommand> observer);

    /**
     * @see androidx.lifecycle.LiveData#removeObservers(LifecycleOwner)
     */
    void removeNavigationObservers(@NonNull LifecycleOwner owner);


}
