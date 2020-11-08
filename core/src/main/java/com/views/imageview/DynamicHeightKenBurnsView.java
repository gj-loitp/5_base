package com.views.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.flaviofaria.kenburnsview.KenBurnsView;

//TODO convert kotlin
public class DynamicHeightKenBurnsView extends KenBurnsView {
    private float mAspectRatio = 1.5f;

    public DynamicHeightKenBurnsView(Context context) {
        super(context);
    }

    public DynamicHeightKenBurnsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightKenBurnsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
    }
}
