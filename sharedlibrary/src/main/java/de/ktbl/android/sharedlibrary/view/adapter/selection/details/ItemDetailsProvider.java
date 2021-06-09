package de.ktbl.android.sharedlibrary.view.adapter.selection.details;

import androidx.recyclerview.selection.ItemDetailsLookup;

/**
 * Needed to provide a secure method to be able to fetch ItemDetails
 *
 * @param <K> Type of the ItemDetails' key
 *
 * @see androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
 * @see ItemDetailsLookup
 */
public interface ItemDetailsProvider<K> {
    ItemDetailsLookup.ItemDetails<K> getItemDetails();
}
