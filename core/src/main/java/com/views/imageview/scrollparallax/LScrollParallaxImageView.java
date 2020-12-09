package com.views.imageview.scrollparallax;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by gjz on 25/11/2016.
 */

public class LScrollParallaxImageView extends AppCompatImageView implements ViewTreeObserver.OnScrollChangedListener {
    private int[] viewLocation = new int[2];
    private boolean enableScrollParallax = true;

    private ParallaxStyle parallaxStyles;

    public LScrollParallaxImageView(Context context) {
        this(context, null);
    }

    public LScrollParallaxImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LScrollParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!enableScrollParallax || getDrawable() == null) {
            super.onDraw(canvas);
            return;
        }

        if (parallaxStyles != null) {
            getLocationInWindow(viewLocation);
            parallaxStyles.transform(this, canvas, viewLocation[0], viewLocation[1]);
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnScrollChangedListener(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollChanged() {
        if (enableScrollParallax) {
            invalidate();
        }
    }

    public void setParallaxStyles(ParallaxStyle styles) {
        if (parallaxStyles != null) {
            parallaxStyles.onDetachedFromImageView(this);
        }
        parallaxStyles = styles;
        parallaxStyles.onAttachedToImageView(this);
    }

    public void setEnableScrollParallax(boolean enableScrollParallax) {
        this.enableScrollParallax = enableScrollParallax;
    }

    public interface ParallaxStyle {
        void onAttachedToImageView(LScrollParallaxImageView view);

        void onDetachedFromImageView(LScrollParallaxImageView view);

        void transform(LScrollParallaxImageView view, Canvas canvas, int x, int y);
    }
}
