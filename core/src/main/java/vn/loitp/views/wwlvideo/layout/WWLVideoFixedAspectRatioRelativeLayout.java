package vn.loitp.views.wwlvideo.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import loitp.core.R;

/**
 * Created by thangn on 2/26/17.
 */

public class WWLVideoFixedAspectRatioRelativeLayout extends RelativeLayout {
    public float mAspectRatio;

    public WWLVideoFixedAspectRatioRelativeLayout(Context context) {
        super(context);
        this.mAspectRatio = 1.0f;
    }

    public WWLVideoFixedAspectRatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioRelativeLayout);
        this.mAspectRatio = typedArray.getFraction(R.styleable.FixedAspectRatioRelativeLayout_aspectRatioRelativeLayout, 1, 1, 1.0f);
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
