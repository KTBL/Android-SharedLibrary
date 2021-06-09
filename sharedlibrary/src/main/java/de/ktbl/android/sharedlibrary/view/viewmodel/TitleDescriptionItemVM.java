package de.ktbl.android.sharedlibrary.view.viewmodel;

import androidx.arch.core.util.Function;
import androidx.navigation.NavDirections;

import de.ktbl.android.sharedlibrary.navigation.NavigationCommand;
import de.ktbl.android.sharedlibrary.util.Strings;
import de.ktbl.android.sharedlibrary.view.adapter.Clickable;
import timber.log.Timber;

public class TitleDescriptionItemVM<T> extends AbstractBaseViewModel implements Clickable {

    private static final String TAG = TitleDescriptionItemVM.class.getSimpleName();

    public static final int DEFAULT_DESCRIPTION_LENGTH = 100;

    public final String title;
    public final String description;
    public final T id;
    private boolean clickEnabled;
    private final Function<T, NavDirections> navigationSupplier;


    public TitleDescriptionItemVM(String title, String description, T id,
                                  Function<T, NavDirections> navigationSupplier) {
        this(title, description, id, DEFAULT_DESCRIPTION_LENGTH, navigationSupplier);

    }


    public TitleDescriptionItemVM(String title, String description, T id,
                                  int description_length,
                                  Function<T, NavDirections> navigationSupplier) {
        this.title = title;
        // If the description has more than 40 characters we have to shorten it
        this.description = Strings.shorten(description, description_length, true);
        this.id = id;
        this.navigationSupplier = navigationSupplier;
        this.enableClick(true);
    }


    @Override
    public void enableClick(boolean enabled) {
        this.clickEnabled = enabled;
    }

    public void onClick() {
        if(!this.clickEnabled ||this.navigationSupplier == null) {
            return;
        }
        NavDirections toNavigateTo = this.navigationSupplier.apply(this.id);
        if(toNavigateTo == null) {
            Timber.tag(TAG).w("NavigationSupplier returned null. Therefore no navigation is possible." );
            return;
        }
        this.navigationEventHandler.notifyLiveEvent(new NavigationCommand(toNavigateTo));
    }

    public void showDetails() {
        if(!this.clickEnabled ||this.navigationSupplier == null) {
            return;
        }
        NavDirections toNavigateTo = this.navigationSupplier.apply(this.id);
        if(toNavigateTo == null) {
            Timber.tag(TAG).w("NavigationSupplier returned null. Therefore no navigation is possible." );
            return;
        }
        this.navigationEventHandler.notifyLiveEvent(new NavigationCommand(toNavigateTo));
    }


}