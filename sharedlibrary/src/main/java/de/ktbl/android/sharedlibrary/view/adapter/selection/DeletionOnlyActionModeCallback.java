package de.ktbl.android.sharedlibrary.view.adapter.selection;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.view.ActionMode;

import de.ktbl.android.sharedlibrary.R;

/**
 * Baseclass to be used as {@link ActionMode.Callback} if only a deletion operation needs
 * to be provided for the multiselection mode.
 * To use this class override {@link #deleteSelectedItems()}
 */
public abstract class DeletionOnlyActionModeCallback implements ActionMode.Callback {


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.deletion_only_action_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_mode_delete) {
            this.deleteSelectedItems();
            return true;
        }
        return false;
    }

    /**
     * This method is called if the user clicked the delete button.
     */
    protected abstract void deleteSelectedItems();
}
