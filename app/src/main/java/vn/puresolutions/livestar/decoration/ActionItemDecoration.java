package vn.puresolutions.livestar.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author hoangphu
 * @since 9/24/16
 */
public class ActionItemDecoration extends RecyclerView.ItemDecoration {
    private int padding;

    public ActionItemDecoration(int padding) {
        this.padding = padding;
    }

    public ActionItemDecoration(Context context, @DimenRes int resId) {
        this.padding = context.getResources().getDimensionPixelSize(resId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = padding;
        outRect.right = padding;
        outRect.bottom = padding;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = (int) (padding * 2.5);
        } else {
            outRect.top = 0;
        }
    }
}