package vn.loitp.app.activity.customviews.watchwhilelayout.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by thangn on 2/27/17.
 */

public class WWLMusicGridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public WWLMusicGridSpacingItemDecoration(int spacing, boolean includeEdge) {
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();

        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position >= spanCount) {
                outRect.top = spacing;
            }
        }
    }
}
