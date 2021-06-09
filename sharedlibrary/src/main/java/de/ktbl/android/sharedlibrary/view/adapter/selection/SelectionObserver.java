package de.ktbl.android.sharedlibrary.view.adapter.selection;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.util.Consumer;
import androidx.recyclerview.selection.SelectionTracker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.ktbl.android.sharedlibrary.view.activity.BarHider;

/**
 * Generic implementation of a {@link SelectionTracker.SelectionObserver}.
 * This SelectionObserver makes use of:
 * <ul>
 *     <li> {@link BarHider} to hide any {@link androidx.appcompat.widget.Toolbar} if the list enters
 *     the selection mode and shows it again on exit.</li>
 * </ul>
 */
public class SelectionObserver<T> extends SelectionTracker.SelectionObserver<T> {
    private final AppCompatActivity activity;
    private final BarHider hideableBar;
    private final SelectionTracker<T> selectionTracker;
    private ActionMode actionMode;
    private final List<Consumer<Boolean>> selectionStateConsumer;
    private final ActionMode.Callback actionModeCallback;
    private final int titlePluralStringId;

    /**
     * @param selectionTracker    the {@link SelectionTracker} this observer will listen to
     * @param activity            the activity which hosts the {@link SelectionTracker} this observer listens to
     * @param actionModeCallback  to be used to create an {@link ActionMode} as needed.
     * @param titlePluralStringId the pluralized {@link String} used for the title of an active {@link ActionMode}
     * @param barHider            in case the {@link ActionMode} gets activated we want to hide any active {@link androidx.appcompat.widget.Toolbar}
     */
    @SuppressWarnings({"unused"})
    public SelectionObserver(@NotNull SelectionTracker<T> selectionTracker,
                             @NotNull AppCompatActivity activity,
                             @NotNull ActionMode.Callback actionModeCallback,
                             int titlePluralStringId,
                             @Nullable BarHider barHider) {
        super();
        this.selectionTracker = selectionTracker;
        this.activity = activity;
        this.hideableBar = barHider;
        this.selectionStateConsumer = new ArrayList<>();
        this.actionModeCallback = actionModeCallback;
        this.titlePluralStringId = titlePluralStringId;
    }

    @SuppressWarnings({"unused"})
    public SelectionObserver(@NotNull SelectionTracker<T> selectionTracker,
                             @NotNull AppCompatActivity activity,
                             @NotNull ActionMode.Callback actionModeCallback,
                             int titlePluralStringId) {
        this(selectionTracker, activity, actionModeCallback,
             titlePluralStringId, null);
    }


    /**
     *
     */
    public void clearActionMode() {
        if (this.actionMode == null) {
            return;
        }
        this.destroyActionMode();
    }

    private void hideBar(boolean hide) {
        if (this.hideableBar != null) {
            if (hide) {
                this.hideableBar.hideBar();
            } else {
                this.hideableBar.showBar();
            }
        }
    }

    @Override
    public void onItemStateChanged(
            @NonNull
                    T key, boolean selected) {
        super.onItemStateChanged(key, selected);
    }

    @Override
    public void onSelectionChanged() {
        super.onSelectionChanged();
        if (this.selectionTracker.hasSelection() && this.actionMode == null) {
            this.startActionMode();

        } else if (!selectionTracker.hasSelection() && actionMode != null) {
            this.destroyActionMode();
            return;
        }

        if (actionMode != null) {
            this.updateTitle();

        }
    }

    @Override
    public void onSelectionRefresh() {
        super.onSelectionRefresh();
    }

    @Override
    public void onSelectionRestored() {
        super.onSelectionRestored();
    }

    /**
     * Register a new observer which will be notified on a selection state change.
     * This notification can be used to disable / enable specific features while a
     * selection is active / inactive
     *
     * @param consumer the consumer to notify on selection state changes
     * @see #removeSelectionStateObserver(Consumer)
     */
    public void registerSelectionStateObserver(Consumer<Boolean> consumer) {
        if (!this.selectionStateConsumer.contains(consumer)) {
            this.selectionStateConsumer.add(consumer);
        }
    }

    /**
     * Removes the given consumer from the notification list.
     *
     * @param consumer the consumer to remove
     * @see #registerSelectionStateObserver(Consumer)
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    public void removeSelectionStateObserver(Consumer<Boolean> consumer) {
        this.selectionStateConsumer.remove(consumer);
    }

    /**
     * Destroys the action mode if any is active.
     * When done the selection state will be cleaned up.
     * <ul>
     * <li>notify all listeners to enable / disable state specific features</li>
     * <li>reactivate the app toolbar</li>
     * </ul>
     *
     * @see #startActionMode()
     */
    private void destroyActionMode() {
        if (this.actionMode == null) {
            return;
        }
        this.actionMode.finish();
        this.actionMode = null;
        this.hideBar(false);
        this.notifySelectionChange(false);
        this.selectionTracker.clearSelection();
        this.selectionStateConsumer.clear();
    }

    private void notifySelectionChange(boolean selectionActive) {
        for (Consumer<Boolean> consumer : this.selectionStateConsumer) {
            consumer.accept(selectionActive);
        }
    }

    /**
     * @see ActionMode#setTitle(CharSequence)
     */
    private void setTitle(CharSequence sequence) {
        this.actionMode.setTitle(sequence);
    }

    /**
     * Starts the action mode and prepares everything for it.
     * <ul>
     * <li>deactivate the app toolbar</li>
     * <li>notifies all listeners about the selection state change</li>
     * </ul>
     *
     * @see #destroyActionMode()
     */
    private void startActionMode() {
        this.actionMode = this.activity.startSupportActionMode(actionModeCallback);
        this.hideBar(true);
        this.notifySelectionChange(true);
    }

    private void updateTitle() {
        int count = this.selectionTracker.getSelection()
                                         .size();
        String newTitle = this.activity.getResources()
                                       .getQuantityString(this.titlePluralStringId, count, count);
        this.setTitle(newTitle);
    }
}
