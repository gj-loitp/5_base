package com.views.layout.aspectratiolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

import com.R;

public class AspectRatioLayout extends FrameLayout {

    private static final String TAG = "AspectRatioLayout";

    private float widthRatio;
    private float heightRatio;

    public AspectRatioLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AspectRatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AspectRatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AspectRatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioLayout, defStyleAttr, defStyleRes);
        widthRatio = a.getFloat(R.styleable.AspectRatioLayout_arl_widthRatio, 1);
        heightRatio = a.getFloat(R.styleable.AspectRatioLayout_arl_heightRatio, 1);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            height = Math.round(heightRatio / widthRatio * width);
            heightMode = MeasureSpec.EXACTLY;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            width = Math.round(widthRatio / heightRatio * height);
            widthMode = MeasureSpec.EXACTLY;
        } else {
            Log.w(TAG, "Width or height are not exact, so do nothing.");
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public float getWidthRatio() {
        return widthRatio;
    }

    public float getHeightRatio() {
        return heightRatio;
    }

    public float getAspectRatio() {
        return widthRatio / heightRatio;
    }

    public void setAspectRatio(float widthRatio, float heightRatio) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        requestLayout();
    }

}
