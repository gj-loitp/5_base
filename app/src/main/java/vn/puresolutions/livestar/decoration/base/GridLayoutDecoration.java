package vn.puresolutions.livestar.decoration.base;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridLayoutDecoration extends RecyclerView.ItemDecoration {

    private int columnCount;
    private int padding;

    public GridLayoutDecoration(int columnCount, int padding) {
        this.columnCount = columnCount;
        this.padding = padding;
    }

    public GridLayoutDecoration(int columnCount, Context context, @DimenRes int padding) {
        this.columnCount = columnCount;
        this.padding = context.getResources().getDimensionPixelSize(padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int column = position % columnCount;

        outRect.left = padding - column * padding / columnCount;
        outRect.right = (column + 1) * padding / columnCount;

        if (position < columnCount) {
            outRect.top = padding;
        }
        outRect.bottom = padding;
    }
}