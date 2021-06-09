package de.ktbl.android.sharedlibrary.navigation;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

/**
 * Data class to describe a navigation transition.
 * <p>
 * TODO: A good thing to have in here would also some
 * Exception handling. Currently we only support
 * handled / not handled. Similar something like
 * if hasNotBeenHandled() -> hasFailed()
 */
public class NavigationCommand {
    public final NavDirections navDirection;
    private boolean hasBeenHandled;

    /**
     * Creates a NavigationCommand to request a navigation to the given NavDirection
     * @param navDirection to navigation to
     */
    public NavigationCommand(@NonNull  NavDirections navDirection) {
        this.navDirection = navDirection;
    }

    /**
     * Creates a NavigationCommand which requests a navigation back to the view before the
     * view requesting this back navigation.
     */
    public NavigationCommand() {
        this.navDirection = null;
    }

    /**
     * Should be called after the navigation command has been executed
     */
    public void setHandled() {
        this.hasBeenHandled = true;
    }

    /**
     * @return true if this NavigationCommand has already been handled, else false
     */
    public boolean hasNotBeenHandled() {
        return !this.hasBeenHandled;
    }

    /**
     * @return true if this NavigationCommand requests a back navigation, else false
     */
    public boolean isNavigateBackCommand() {
        return this.navDirection == null;
    }
}
