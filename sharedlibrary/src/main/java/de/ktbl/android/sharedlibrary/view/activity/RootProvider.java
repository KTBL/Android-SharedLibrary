package de.ktbl.android.sharedlibrary.view.activity;

import android.view.View;

/**
 * Interface declaring a method to provide the root-view of the
 * implementing Activity, Fragment, or similar.
 */
public interface RootProvider {
    View getRoot();
}
