package vn.loitp.app.activity.customviews.wwlmusic.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import loitp.basemaster.R;

/**
 * Created by thangn on 3/1/17.
 */

public class WWLMusicFixedAspectRatioFrameLayout extends FrameLayout {
    public float mAspectRatio;

    public WWLMusicFixedAspectRatioFrameLayout(Context context) {
        super(context);
        this.mAspectRatio = 1.0f;
    }

    public WWLMusicFixedAspectRatioFrameLayout(Context context, AttributeSet attrs) {
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
