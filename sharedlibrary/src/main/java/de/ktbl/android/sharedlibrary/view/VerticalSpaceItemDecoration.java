package de.ktbl.android.sharedlibrary.view;

import android.graphics.Rect;
import android.view.View;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != Objects.requireNonNull(parent.getAdapter())
                                                           .getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}