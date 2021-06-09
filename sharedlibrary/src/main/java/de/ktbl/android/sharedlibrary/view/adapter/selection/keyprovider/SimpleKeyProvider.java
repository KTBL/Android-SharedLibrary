package de.ktbl.android.sharedlibrary.view.adapter.selection.keyprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

/**
 * Simple mapped implementation of
 * {@link de.ktbl.android.sharedlibrary.view.adapter.selection.details.ItemDetailsProvider<Long>}
 * Directly maps a position to a key and a key to a position.
 */
public class SimpleKeyProvider extends ItemKeyProvider<Long> {

    public SimpleKeyProvider() {
        super(ItemKeyProvider.SCOPE_MAPPED);
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) position;
    }

    @Override
    public int getPosition(
            @NonNull
                    Long key) {
        return key.intValue();
    }
}
