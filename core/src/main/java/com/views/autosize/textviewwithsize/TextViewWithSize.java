package com.views.autosize.textviewwithsize;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.R;
import com.core.utilities.LUIUtil;

/**
 * Created by LENOVO on 4/19/2018.
 */

public class TextViewWithSize extends AppCompatTextView {
    private final String logTag = getClass().getSimpleName();

    public TextViewWithSize(Context context) {
        super(context);
        //initSizeScreenW();
        init();
    }

    public TextViewWithSize(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initSizeScreenW();
        init();
    }

    public TextViewWithSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initSizeScreenW();
        init();
    }

//    public TextViewWithSize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        //initSizeScreenW();
//        init();
//    }

    private void init() {
        LUIUtil.Companion.setTextShadow(this, Color.RED);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_18));
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_12));
        }
    }
}
