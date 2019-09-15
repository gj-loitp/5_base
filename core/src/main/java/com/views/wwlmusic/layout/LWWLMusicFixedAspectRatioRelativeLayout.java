package com.views.wwlmusic.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.R;

public class LWWLMusicFixedAspectRatioRelativeLayout extends RelativeLayout {
    public float mAspectRatio;

    public LWWLMusicFixedAspectRatioRelativeLayout(Context context) {
        super(context);
        this.mAspectRatio = 1.0f;
    }

    public LWWLMusicFixedAspectRatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatio);
        this.mAspectRatio = typedArray.getFraction(R.styleable.FixedAspectRatio_aspectRatio, 1, 1, 1.0f);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (((float) MeasureSpec.getSize(widthMeasureSpec)) / this.mAspectRatio), MeasureSpec.EXACTLY));
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            super.onMeasure(MeasureSpec.makeMeasureSpec((int) (((float) MeasureSpec.getSize(heightMeasureSpec)) * this.mAspectRatio), MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
