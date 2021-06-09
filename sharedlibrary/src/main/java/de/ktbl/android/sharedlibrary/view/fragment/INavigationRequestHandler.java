package de.ktbl.android.sharedlibrary.view.fragment;

import androidx.annotation.NonNull;

import de.ktbl.android.sharedlibrary.navigation.NavigationCommand;

/**
 * Very simple interface to be used with ViewModel-base-classes of this library.
 * It only provides a single method, which is to be called if a navigation
 * transition is requested. The class implementing this interface is either
 * responsible to handle this request or has to redirect it to the responsible
 * object.
 */
public interface INavigationRequestHandler {
    /**
     * Called to request a navigation transition.
     * If the transition is handled the {@link NavigationCommand#setHandled()}
     * has to be called to signal this.
     *
     * @param navigationCommand describes the requested navigation transition
     */
    void onNavigationRequest(@NonNull NavigationCommand navigationCommand);
}
