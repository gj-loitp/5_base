package vn.loitp.views.shadowviewhelper;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

/**
 * <code>
 * if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
 * view.setLayerType(View.LAYER_TYPE_SOFTWARE, drawable.getPaint());
 * }
 * </code>
 * <p/>
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/2/15.
 */
public class ShadowViewDrawable extends Drawable {
    private Paint paint;
    private RectF bounds = new RectF();
    private int width;
    private int height;
    private ShadowProperty shadowProperty;
    private int shadowOffset;
    private RectF drawRect;
    private float rx;
    private float ry;

    public ShadowViewDrawable(ShadowProperty shadowProperty, int color, float rx, float ry) {
        this.shadowProperty = shadowProperty;
        shadowOffset = this.shadowProperty.getShadowOffset();

        this.rx = rx;
        this.ry = ry;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setShadowLayer(shadowProperty.getShadowRadius(), shadowProperty.getShadowDx(), shadowProperty.getShadowDy(), shadowProperty.getShadowColor());

        drawRect = new RectF();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (bounds.right - bounds.left > 0 && bounds.bottom - bounds.top > 0) {
            this.bounds.left = bounds.left;
            this.bounds.right = bounds.right;
            this.bounds.top = bounds.top;
            this.bounds.bottom = bounds.bottom;
            width = (int) (this.bounds.right - this.bounds.left);
            height = (int) (this.bounds.bottom - this.bounds.top);

//            drawRect = new RectF(shadowOffset, shadowOffset, width - shadowOffset, height - shadowOffset);
//            drawRect = new RectF(0, 0, width, height - shadowOffset);

            final int shadowSide = shadowProperty.getShadowSide();
            final int left = (shadowSide & ShadowProperty.Companion.getLEFT()) == ShadowProperty.Companion.getLEFT() ? shadowOffset : 0;
            final int top = (shadowSide & ShadowProperty.Companion.getTOP()) == ShadowProperty.Companion.getTOP() ? shadowOffset : 0;
            final int right = width - ((shadowSide & ShadowProperty.Companion.getRIGHT()) == ShadowProperty.Companion.getRIGHT() ? shadowOffset : 0);
            final int bottom = height - ((shadowSide & ShadowProperty.Companion.getBOTTOM()) == ShadowProperty.Companion.getBOTTOM() ? shadowOffset : 0);

            drawRect = new RectF(left, top, right, bottom);

            invalidateSelf();
        }
    }

    private PorterDuffXfermode srcOut = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

    @Override
    public void draw(@NonNull Canvas canvas) {
        paint.setXfermode(null);

        canvas.drawRoundRect(
                drawRect,
                rx, ry,
                paint
        );

        paint.setXfermode(srcOut);
//        paint.setColor(Color.TRANSPARENT);
        canvas.drawRoundRect(drawRect, rx, ry, paint);
    }

    public ShadowViewDrawable setColor(int color) {
        paint.setColor(color);
        return this;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
