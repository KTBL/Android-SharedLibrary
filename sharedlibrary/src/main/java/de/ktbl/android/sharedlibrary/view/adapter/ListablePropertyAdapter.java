package de.ktbl.android.sharedlibrary.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import java.util.List;

import de.ktbl.android.sharedlibrary.BR;
import de.ktbl.android.sharedlibrary.R;
import de.ktbl.android.sharedlibrary.databinding.AdapterListablePropertyBinding;

public class ListablePropertyAdapter extends BaseBindingAdapter<AdapterListablePropertyBinding,
        de.ktbl.android.sharedlibrary.view.viewmodel.AdapterListablePropertyVM> {

    public ListablePropertyAdapter(@Nullable
                                           List<de.ktbl.android.sharedlibrary.view.viewmodel.AdapterListablePropertyVM> dataset,
                                   LifecycleOwner lifecycleOwner) {
        super(AdapterListablePropertyBinding::bind,
              R.layout.adapter_listable_property,
              dataset,
              lifecycleOwner);
    }

    @Override
    protected void bindData(@NonNull
                                    de.ktbl.android.sharedlibrary.view.viewmodel.AdapterListablePropertyVM item,
                            @NonNull BindableViewHolder<AdapterListablePropertyBinding> holder) {
        holder.getViewBinding().setVariable(BR.vm, item);
    }
}
