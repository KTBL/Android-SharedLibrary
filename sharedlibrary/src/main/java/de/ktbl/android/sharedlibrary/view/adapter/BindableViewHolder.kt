package de.ktbl.android.sharedlibrary.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.widget.RecyclerView
import de.ktbl.android.sharedlibrary.view.adapter.selection.details.ItemDetailsProvider
import de.ktbl.android.sharedlibrary.view.adapter.selection.details.SimpleItemDetails

/**
 * Baseclass to be used to create [androidx.recyclerview.widget.RecyclerView.ViewHolder]
 * which use bindings instead of direct view access.
 * This implementation also includes an base to be used with [androidx.recyclerview.selection.SelectionTracker]
 * of type [Long]
 *
 * @param <T> the [ViewDataBinding] to be used.
 *
 * @see ItemDetailsProvider
 *
 * @see androidx.recyclerview.selection.SelectionTracker
</T> */
class BindableViewHolder<T : ViewDataBinding?> constructor(itemView: View, val viewBinding: T) : RecyclerView.ViewHolder(
        itemView), ItemDetailsProvider<Long?> {
    private val details: SimpleItemDetails = SimpleItemDetails()

    /**
     * Sets the view hold by this object as active or inactive
     * @param isSelected wether this holder is selected or not
     */
    fun setSelected(isSelected: Boolean) {
        viewBinding!!.root.isActivated = isSelected
    }

    /**
     * Sets the position of this view holder.
     * This is needed since the key of the item as well as the position is used to identify items
     * @param position new position
     */
    fun setPosition(position: Int) {
        details.position = position
    }

    /**
     * @see ItemDetailsProvider
     */
    override fun getItemDetails(): ItemDetails<Long?>? {
        return details
    }

}