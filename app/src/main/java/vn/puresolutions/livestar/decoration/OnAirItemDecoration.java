package vn.puresolutions.livestar.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.OnAirAdapter;

/**
 * Created by Phu Tran on 8/11/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class OnAirItemDecoration extends RecyclerView.ItemDecoration {
    private int padding;
    private int topPadding;

    public OnAirItemDecoration(int padding) {
        this.padding = padding;
    }

    public OnAirItemDecoration(Context context, @DimenRes int resID) {
        this.padding = context.getResources().getDimensionPixelSize(resID);
        this.topPadding = context.getResources().getDimensionPixelSize(R.dimen.on_air_item_padding_top);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = padding;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = topPadding;
        } else {
            outRect.top = 0;
        }
    }
}