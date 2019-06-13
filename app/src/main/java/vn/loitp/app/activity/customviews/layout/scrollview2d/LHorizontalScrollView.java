package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;

public class LHorizontalScrollView extends HorizontalScrollView {

    private ScrollListener mScrollListener;

    public LHorizontalScrollView(Context context) {
        super(context);
    }

    public LHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOnScrollListener(@NonNull final ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null) {
            mScrollListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public interface ScrollListener {
        void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}