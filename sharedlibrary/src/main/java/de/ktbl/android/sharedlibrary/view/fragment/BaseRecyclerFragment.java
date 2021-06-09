package de.ktbl.android.sharedlibrary.view.fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.ktbl.android.sharedlibrary.view.activity.AbstractBaseActivity;
import de.ktbl.android.sharedlibrary.view.adapter.BaseBindingAdapter;
import de.ktbl.android.sharedlibrary.view.adapter.Clickable;
import de.ktbl.android.sharedlibrary.view.adapter.selection.SelectionObserver;
import de.ktbl.android.sharedlibrary.view.adapter.selection.details.DetailsLookup;
import de.ktbl.android.sharedlibrary.view.adapter.selection.keyprovider.SimpleKeyProvider;
import timber.log.Timber;

public abstract class BaseRecyclerFragment
        extends AbstractBaseFragment {
    protected SelectionObserver<Long> selectionObserver;
    protected SelectionTracker<Long> selectionTracker;

    public static String TAG = BaseRecyclerFragment.class.getSimpleName();


    @Override
    public void onPause() {
        super.onPause();
        // Clear the action mode possibly active by this fragment's RecyclerView
        // if this fragment is paused - e.g. when switching to another fragment.
        // This is needed since else the action mode survives the switch and stays
        // active all the time.
        if (this.selectionObserver != null) {
            this.selectionObserver.clearActionMode();
        }
    }

    /**
     * Sets up the {@link androidx.recyclerview.widget.RecyclerView.ItemDecoration} of a {@link RecyclerView}
     *
     * @param recyclerView   the {@link RecyclerView} to set up
     * @param itemDecoration this is optional. If the custom {@link RecyclerView.ItemDecoration} is
     *                       null a default {@link DividerItemDecoration} is used.
     */
    protected void setupRecyclerView(RecyclerView recyclerView,
                                     @Nullable RecyclerView.ItemDecoration itemDecoration) {
        if (this.getContext() == null) {
            Timber.tag(TAG)
                  .wtf("Called setupRecyclerview and this.getContext() == null");
            throw new NullPointerException();
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) {
            Timber.tag(TAG)
                  .e("Called setupRecyclerView with recyclerView.getLayoutManager() == null");
            throw new NullPointerException();
        }

        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        } else {
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),
                                                                     layoutManager.getOrientation()));
        }
    }

    /**
     * Creates and initializes a {@link SelectionTracker} for this {@link androidx.fragment.app.Fragment}'s
     * {@link RecyclerView}.
     *
     * @param view                recyclerview to setup the {@link SelectionTracker} for.
     * @param adapter             the adapter used for the items displayed in the {@link RecyclerView}
     * @param pluralTitleStringId resource id of the pluralized string to be used as title of the
     *                            active ActionMode
     * @param selectionId         the unique ID to be used to identify this selectionTracker
     * @param actionModeCallBack  the callback to setup and handle the to be created {@link ActionMode}
     */
    protected <T extends ViewDataBinding, V extends Clickable> void setupSelectionTracker(
            RecyclerView view,
            BaseBindingAdapter<T, V> adapter,
            int pluralTitleStringId,
            String selectionId,
            ActionMode.Callback actionModeCallBack) {
        this.selectionTracker = new SelectionTracker.Builder<>(
                selectionId,
                view,
                new SimpleKeyProvider(),
                new DetailsLookup(view),
                StorageStrategy.createLongStorage()
        ).build();
        if (!(this.getActivity() instanceof AbstractBaseActivity)) {
            throw new ClassCastException("The activity using "
                                         + adapter.getClass()
                                                  .getSimpleName()
                                         + " must extend AbstractBaseActivity");
        }
        // since AbstractBaseActivity extends AppCompatActivity this cast should never
        // fail if the above check does not throw an Exception
        AppCompatActivity activity = (AppCompatActivity) this.getActivity();
        AbstractBaseActivity baseActivity = (AbstractBaseActivity) activity;
        this.selectionObserver = new SelectionObserver<>(this.selectionTracker,
                                                         activity,
                                                         actionModeCallBack,
                                                         pluralTitleStringId,
                                                         baseActivity);
        this.selectionTracker.addObserver(this.selectionObserver);
        // Registration needed so the adapter is able to disable the navigation call
        // if an element is clicked.
        this.selectionObserver.registerSelectionStateObserver(adapter::onSelectionStateChange);
        adapter.setSelectionTracker(this.selectionTracker);
    }

}
