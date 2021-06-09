package de.ktbl.android.sharedlibrary.view.adapter.selection.details;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

/**
 * Simple implementation of {@link ItemDetailsLookup.ItemDetails<Long>}.
 * The position of the item also used as the key.
 *
 * @see androidx.recyclerview.selection.SelectionTracker
 * @see androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
 * @see de.ktbl.android.sharedlibrary.view.adapter.selection.keyprovider.SimpleKeyProvider
 * @see DetailsLookup
 */
public class SimpleItemDetails extends ItemDetailsLookup.ItemDetails<Long> {
    private int position;

    @Override
    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        return (long) this.position;
    }
}
