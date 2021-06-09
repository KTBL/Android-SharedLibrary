package de.ktbl.android.sharedlibrary.view.activity;

/**
 * Declares methods to show / hide the {@link android.app.ActionBar} of a
 * {@link androidx.fragment.app.Fragment} or {@link android.app.Activity}.
 * This interface is used by the {@link de.ktbl.android.sharedlibrary.view.fragment.BaseRecyclerFragment}
 * to automatically hide the bar if the multi-selection-mode is in use and
 * show it afterwards again.
 */
public interface BarHider {
    void hideBar();

    void showBar();
}
