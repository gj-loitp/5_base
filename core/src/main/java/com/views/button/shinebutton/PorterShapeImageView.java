package com.views.button.shinebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.R;

public class PorterShapeImageView extends PorterImageView {
    private Drawable shape;
    private Matrix matrix;
    private Matrix drawMatrix;

    public PorterShapeImageView(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public PorterShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public PorterShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs, defStyle);
    }

    private void setup(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PorterImageView, defStyle, 0);
            shape = typedArray.getDrawable(R.styleable.PorterImageView_siShape);
            typedArray.recycle();
        }
        matrix = new Matrix();
    }

    void setShape(Drawable drawable) {
        shape = drawable;
        invalidate();
    }

    @Override
    protected void paintMaskCanvas(Canvas maskCanvas, Paint maskPaint, int width, int height) {
        if (shape != null) {
            if (shape instanceof BitmapDrawable) {
                configureBitmapBounds(getWidth(), getHeight());
                if (drawMatrix != null) {
                    final int drawableSaveCount = maskCanvas.getSaveCount();
                    maskCanvas.save();
                    maskCanvas.concat(matrix);
                    shape.draw(maskCanvas);
                    maskCanvas.restoreToCount(drawableSaveCount);
                    return;
                }
            }

            shape.setBounds(0, 0, getWidth(), getHeight());
            shape.draw(maskCanvas);
        }
    }

    private void configureBitmapBounds(final int viewWidth, final int viewHeight) {
        drawMatrix = null;
        final int drawableWidth = shape.getIntrinsicWidth();
        final int drawableHeight = shape.getIntrinsicHeight();
        final boolean fits = viewWidth == drawableWidth && viewHeight == drawableHeight;

        if (drawableWidth > 0 && drawableHeight > 0 && !fits) {
            shape.setBounds(0, 0, drawableWidth, drawableHeight);
            final float widthRatio = (float) viewWidth / (float) drawableWidth;
            final float heightRatio = (float) viewHeight / (float) drawableHeight;
            final float scale = Math.min(widthRatio, heightRatio);
            final float dx = (int) ((viewWidth - drawableWidth * scale) * 0.5f + 0.5f);
            final float dy = (int) ((viewHeight - drawableHeight * scale) * 0.5f + 0.5f);

            matrix.setScale(scale, scale);
            matrix.postTranslate(dx, dy);
        }
    }
}