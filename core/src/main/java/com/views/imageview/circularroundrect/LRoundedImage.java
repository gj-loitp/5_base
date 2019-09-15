package com.views.imageview.circularroundrect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.R;

public class LRoundedImage extends AppCompatImageView {

    private RectF rect;
    private Path path = new Path();
    private float DEFAULT_RADIUS = 20f;
    private float radius = DEFAULT_RADIUS;
    private final ScaleType scaleType = ScaleType.FIT_XY;

    public LRoundedImage(Context context) {
        super(context);
        setScaleType(scaleType);
        init();
    }

    public LRoundedImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setScaleType(scaleType);
        init();
    }

    public LRoundedImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(scaleType);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LRoundedImage, defStyleAttr, 0);
        radius = a.getFloat(R.styleable.LRoundedImage_rounded_radius, DEFAULT_RADIUS);
        init();
    }

    public int roundRadius() {
        return (int) radius;
    }

    public void setRoundedRadius(int radius) {
        this.radius = radius;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    private void init() {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CCW);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }
}