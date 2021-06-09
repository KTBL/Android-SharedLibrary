package de.ktbl.android.sharedlibrary.view.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import de.ktbl.android.sharedlibrary.annotation.model.ListablePropertyModel;
import de.ktbl.android.sharedlibrary.view.adapter.Clickable;

public class AdapterListablePropertyVM extends ViewModel implements Clickable {
    public final boolean hasBeenEdited;
    public final String header;
    public final boolean isEditable;
    public final String unit;
    private final String content;
    private final ListablePropertyModel listableProperty;

    public AdapterListablePropertyVM(@NonNull ListablePropertyModel listableProperty) {
        this.listableProperty = listableProperty;
        this.header = this.listableProperty.getDescription();
        this.content = this.listableProperty.getValue();
        this.unit = this.listableProperty.getUnit();
        this.isEditable = this.listableProperty.isEditable();
        this.hasBeenEdited = false;
    }

    @Override public void enableClick(boolean enabled) {
        // nope
    }

    public String getContent() {
        return content;
    }

    public ListablePropertyModel getListableProperty() {
        return listableProperty;
    }
}
