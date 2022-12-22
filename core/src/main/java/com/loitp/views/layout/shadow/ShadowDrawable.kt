package com.loitp.views.layout.shadow

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ShadowDrawable(
    private val mShape: Int,
    shadowColor: Int,
    private val mShadowRadius: Float,
    private val mOffsetX: Float,
    private val mOffsetY: Float
) : Drawable() {

    private val mShadowPaint: Paint = Paint()
    private var mRect: RectF? = null

    override fun setBounds(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.setBounds(left, top, right, bottom)
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds left " + left);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds top " + top);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds right " + right);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds bottom " + bottom);
        mRect = RectF(
            left + mShadowRadius - mOffsetX,
            top + mShadowRadius - mOffsetY,
            right - mShadowRadius - mOffsetX,
            bottom - mShadowRadius - mOffsetY
        )
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds mRect.left " + mRect.left);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds mRect.top " + mRect.top);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds mRect.right " + mRect.right);
        // Log.i("ShadowLayout1", "ShadowDrawable1 setBounds mRect.bottom " + mRect.bottom);
    }

    override fun draw(canvas: Canvas) {
        // Log.i("ShadowLayout3", "ShadowDrawable1 draw " + canvas);
        mRect?.let { r ->
            if (mShape == LShadowLayout2.SHAPE_RECTANGLE) {
                canvas.drawRect(r, mShadowPaint)
            } else if (mShape == LShadowLayout2.SHAPE_OVAL) {
                canvas.drawCircle(
                    r.centerX(),
                    r.centerY(),
                    min(r.width(), r.height()) / 2,
                    mShadowPaint
                )
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        mShadowPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mShadowPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    init {
        mShadowPaint.color = Color.TRANSPARENT
        mShadowPaint.isAntiAlias = true
        mShadowPaint.setShadowLayer(mShadowRadius, mOffsetX, mOffsetY, shadowColor)
        mShadowPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
    }
}
