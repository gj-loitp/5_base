package vn.puresolutions.livestar.custom.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import java.lang.ref.WeakReference;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/26/2015
 */
public class ImageSpan extends DynamicDrawableSpan {

    private final int mSize;

    private final int mTextSize;

    private int mHeight;

    private int mWidth;

    private int mTop;

    private Bitmap mBitmap;

    private Drawable mDrawable;

    private WeakReference<Drawable> mDrawableRef;

    public ImageSpan(Bitmap bitmap, int size, int textSize) {
        super(DynamicDrawableSpan.ALIGN_BASELINE);
        mBitmap = bitmap;
        mWidth = mHeight = mSize = size;
        mTextSize = textSize;
    }

    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                mDrawable = new BitmapDrawable(mBitmap);
                mHeight = mSize;
                mWidth = mHeight * mDrawable.getIntrinsicWidth() / mDrawable.getIntrinsicHeight();
                mTop = (mTextSize - mHeight) / 2;
                mDrawable.setBounds(0, mTop, mWidth, mTop + mHeight);
            } catch (Exception e) {
                // swallow
            }
        }
        return mDrawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY = top + ((bottom - top) / 2) - ((b.getBounds().bottom - b.getBounds().top) / 2) - mTop;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        if (mDrawableRef == null || mDrawableRef.get() == null) {
            mDrawableRef = new WeakReference<Drawable>(getDrawable());
        }
        return mDrawableRef.get();
    }
}
