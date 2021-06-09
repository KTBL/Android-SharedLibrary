package de.ktbl.android.sharedlibrary.view.adapter.selection.details;

import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * Used in relation with an {@link SelectionTracker <Long>}.
 * {@link ViewHolder} need to implement {@link ItemDetailsProvider <Long>}
 *
 * @see ItemDetailsLookup
 * @see androidx.recyclerview.selection.SelectionTracker
 * @see ItemDetailsProvider
 */
public class DetailsLookup extends ItemDetailsLookup<Long> {

    private final RecyclerView recyclerView;

    public DetailsLookup(@NotNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked") // ToDO: Possible to check the cast even due to type erasure?
    // Maybe some black-reflection-magic?
    public ItemDetails<Long> getItemDetails(
            @NonNull
                    MotionEvent e) {
        View view = this.recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            ViewHolder holder = this.recyclerView.getChildViewHolder(view);
            if (holder instanceof ItemDetailsProvider) {
                return ((ItemDetailsProvider<Long>) holder).getItemDetails();
            }
        }
        return null;
    }
}
