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
public class ListHorizontalLayoutDecoration extends RecyclerView.ItemDecoration {
    private int padding;

    public ListHorizontalLayoutDecoration(int padding) {
        this.padding = padding;
    }

    public ListHorizontalLayoutDecoration(Context context, @DimenRes int resId) {
        this.padding = context.getResources().getDimensionPixelSize(resId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = padding;
        outRect.bottom = padding;
        outRect.right = padding;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = padding;
        } else {
            outRect.left = 0;
        }
    }
}
