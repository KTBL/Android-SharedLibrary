package de.ktbl.android.sharedlibrary.view.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import de.ktbl.android.sharedlibrary.SnackbarNotificationEvent
import de.ktbl.android.sharedlibrary.livedata.LiveEventHandler
import de.ktbl.android.sharedlibrary.navigation.INavigationRequester
import de.ktbl.android.sharedlibrary.navigation.NavigationCommand

/**
 * Simple basic implementation of
 */
abstract class AbstractBaseViewModel : AbstractLoadSaveViewModel(), INavigationRequester, ISnackbarNotificationRequester {
    @JvmField
    protected var navigationEventHandler = LiveEventHandler<NavigationCommand>()
    protected var snackbarNotificationEventLiveEventHandler = LiveEventHandler<SnackbarNotificationEvent>()
    override fun observeNavigationRequests(owner: LifecycleOwner,
                                           observer: Observer<NavigationCommand>) {
        navigationEventHandler.observe(owner, observer)
    }

    override fun observeNavigationRequestsForever(
            observer: Observer<NavigationCommand>) {
        navigationEventHandler.observeForever(observer)
    }

    override fun removeNavigationObserver(observer: Observer<NavigationCommand>) {
        navigationEventHandler.removeObserver(observer)
    }

    override fun removeNavigationObservers(owner: LifecycleOwner) {
        navigationEventHandler.removeObservers(owner)
    }

    override fun observeSnackbarNotificationRequests(owner: LifecycleOwner,
                                                     observer: Observer<SnackbarNotificationEvent>) {
        snackbarNotificationEventLiveEventHandler.observe(owner, observer)
    }

    override fun removeSnackbarNotificationObservers(owner: LifecycleOwner) {
        snackbarNotificationEventLiveEventHandler.removeObservers(owner)
    }

    public override fun loadDataFromDB(): Boolean {
        return false
    }

    public override fun loadMockedData(): Boolean {
        return false
    }

    public override fun saveDataToDB() {}
}