package com.views.animation.flyschool;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import loitp.core.R;

/**
 * * ShapeView to be used inside {@link ShapeFlyer}
 * for Android < LOLLIPOP
 * <p>
 * Created by avin on 30/01/17.
 */

public class AppCompatShapeView extends AppCompatImageView implements ShapeSetter {
    public AppCompatShapeView(Context context) {
        super(context);
        init();
    }

    public AppCompatShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppCompatShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageResource(R.drawable.facebook_button_blue);
    }

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
