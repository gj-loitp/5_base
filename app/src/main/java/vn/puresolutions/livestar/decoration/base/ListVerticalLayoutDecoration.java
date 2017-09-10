package vn.puresolutions.livestar.decoration.base;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Phu Tran on 3/21/2016.
 */
public class ListVerticalLayoutDecoration extends RecyclerView.ItemDecoration {
    private int padding;

    public ListVerticalLayoutDecoration(int padding) {
        this.padding = padding;
    }

    public ListVerticalLayoutDecoration(Context context, @DimenRes int resId) {
        this.padding = context.getResources().getDimensionPixelSize(resId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = padding;
        outRect.right = padding;
        outRect.bottom = padding;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = padding;
        } else {
            outRect.top = 0;
        }
    }
}