package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import vn.puresolutions.livestar.R;

/**
 * @author hoangphu
 * @since 8/13/16
 */
public class LSProgressBar extends ProgressBar {

    private int filterColor;

    public LSProgressBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LSProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LSProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LSProgressBar, defStyle, 0);
            filterColor = a.getColor(R.styleable.LSProgressBar_filterColor, Color.WHITE);
            a.recycle();
        }
        getIndeterminateDrawable().setColorFilter(filterColor, PorterDuff.Mode.SRC_IN);
    }

    public int getFilterColor() {
        return filterColor;
    }

    public void setFilterColor(int filterColor) {
        this.filterColor = filterColor;
        getIndeterminateDrawable().setColorFilter(filterColor, PorterDuff.Mode.SRC_IN);
    }
}
