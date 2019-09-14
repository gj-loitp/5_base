package com.views.animation.flyschool;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import loitp.core.R;

public class ShapeView extends ImageView implements ShapeSetter {
    public ShapeView(Context context) {
        super(context);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setImageResource(R.drawable.l_facebook_button_blue);
        //int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
    }

    //private int dimensionInDp;

    @Override
    public void setShape(int drawable) {
        setImageResource(drawable);
        Utils.setHeart(this);
    }

    @Override
    public void setShape(ImgObject imgObject, int drawableRes) {
        //setImageResource(R.mipmap.ic_launcher);
    }
}
