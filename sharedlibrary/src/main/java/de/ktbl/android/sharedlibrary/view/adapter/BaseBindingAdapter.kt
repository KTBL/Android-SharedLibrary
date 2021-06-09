package de.ktbl.android.sharedlibrary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

/**
 * Base class to be used with [RecyclerView.Adapter] which are holding
 * [BindableViewHolder].
 * This class implements basic features to manage the lifecycle of the adapter, as well
 * as selection state handling. The Viewholder must implement [Clickable] interface. In
 * case of an active selection the click-functionality will be disabled, and afterward
 * enabled again.
 *
 * @param <T> must extend [ViewDataBinding]
 * @param <V> Datamodel for the [RecyclerView] items, must implement [Clickable]
</V></T> */
abstract class BaseBindingAdapter<T : ViewDataBinding, V : Clickable?>
protected constructor(protected val bindingSupplier: (View) -> T,
                      protected val layoutID: Int,
                      dataset: List<V>,
                      protected val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BindableViewHolder<T>>() {
    var selectionTracker: SelectionTracker<Long>? = null
    var dataset: List<V> = dataset
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * This method needs to be implemented by child classes to set all binding variables.
     *
     * @param item   Dataitem to be binded
     * @param holder ViewHolder which the item needs to be binded with
     */
    protected abstract fun bindData(item: V, holder: BindableViewHolder<T>)


    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: BindableViewHolder<T>, position: Int) {
        val item = dataset[position]
        bindData(item, holder)
        holder.position = position
        selectionTracker?.let {
            holder.setSelected(selectionTracker!!.isSelected(position.toLong()))
        }
        holder.viewBinding.executePendingBindings()
        holder.viewBinding.lifecycleOwner = lifecycleOwner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BindableViewHolder<T> {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(layoutID, viewGroup, false)
        val binding = bindingSupplier(view)
        return BindableViewHolder(view, binding)
    }

    /**
     * Consumer for [SelectionObserver.registerSelectionStateObserver].
     * Disables the click handling of elements within this adapter if a selection is
     * active and re-enables them if it is inactive.
     *
     * @param selectionActive indicates wether the selection mode of the list is active or not.
     *
     * @see SelectionObserver.registerSelectionStateObserver
     */
    fun onSelectionStateChange(selectionActive: Boolean) {
        for (vm in dataset) {
            vm!!.enableClick(!selectionActive)
        }
    }
}