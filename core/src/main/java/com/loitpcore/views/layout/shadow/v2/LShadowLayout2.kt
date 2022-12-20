package com.loitpcore.views.layout.shadow.v2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import com.loitpcore.R
import com.loitp.core.utilities.LAppResource

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LShadowLayout2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val ALL = 0x1111
        const val LEFT = 0x0001
        const val TOP = 0x0010
        const val RIGHT = 0x0100
        const val BOTTOM = 0x1000
        const val SHAPE_RECTANGLE = 0x0001
        const val SHAPE_OVAL = 0x0010
    }

    private var mShadowColor = Color.TRANSPARENT
    private var mShadowRadius = 0f
    private var mShadowDx = 0f
    private var mShadowDy = 0f
    private var mShadowSide = ALL

    private var mShadowShape = SHAPE_RECTANGLE
    private var mShadowDrawable: ShadowDrawable? = null

    @SuppressLint("CustomViewStyleable")
    private fun initialize(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LShadowLayout)
        mShadowShape = typedArray.getInt(R.styleable.LShadowLayout_shadowShape, SHAPE_RECTANGLE)
        mShadowRadius = typedArray.getDimension(R.styleable.LShadowLayout_shadowRadius, 0f)
        mShadowColor = typedArray.getColor(
            R.styleable.LShadowLayout_shadowColor,
            LAppResource.getColor(R.color.black)
        )
        mShadowDx = typedArray.getDimension(R.styleable.LShadowLayout_shadowDx, 0f)
        mShadowDy = typedArray.getDimension(R.styleable.LShadowLayout_shadowDy, 0f)
        mShadowSide = typedArray.getInt(R.styleable.LShadowLayout_shadowSide, ALL)
        typedArray.recycle()
        mShadowDrawable = ShadowDrawable(
            mShape = mShadowShape,
            shadowColor = mShadowColor,
            mShadowRadius = mShadowRadius,
            mOffsetX = mShadowDx,
            mOffsetY = mShadowDy
        )
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    @Suppress("NAME_SHADOWING")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val effect = mShadowRadius
        var rectLeft = 0f
        var rectTop = 0f
        var rectRight = this.measuredWidth.toFloat()
        var rectBottom = this.measuredHeight.toFloat()
        // Log.i("ShadowLayout", "ShadowLayout onMeasure getMeasuredWidth " + this.getMeasuredWidth());
        // Log.i("ShadowLayout", "ShadowLayout onMeasure getMeasuredHeight " + this.getMeasuredHeight());
        this.width
        if (mShadowSide and LEFT == LEFT) {
            rectLeft = -effect
        }
        if (mShadowSide and TOP == TOP) {
            rectTop = -effect
        }
        if (mShadowSide and RIGHT == RIGHT) {
            rectRight = this.measuredWidth + effect
        }
        if (mShadowSide and BOTTOM == BOTTOM) {
            rectBottom = this.measuredHeight + effect
        }
        if (mShadowDy != 0.0f) {
            rectBottom += mShadowDy
        }
        if (mShadowDx != 0.0f) {
            rectRight += mShadowDx
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
            MeasureSpec.getSize((rectRight - rectLeft).toInt()),
            MeasureSpec.EXACTLY
        )
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
            MeasureSpec.getSize((rectBottom - rectTop).toInt()),
            MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Log.i("ShadowLayout", "ShadowLayout onMeasure getMeasuredWidth " + px2dip(this.getMeasuredWidth()));
        // Log.i("ShadowLayout", "ShadowLayout onMeasure getMeasuredHeight " + px2dip(this.getMeasuredHeight()));
    }

    override fun dispatchDraw(canvas: Canvas) {
        // Log.i("ShadowLayout", "ShadowLayout dispatchDraw");
        super.dispatchDraw(canvas)
        ViewCompat.setBackground(this@LShadowLayout2, mShadowDrawable)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        // NO OP
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        // NO OP
    }

//    private fun dp2Px(dpValue: Float): Float {
//        val dm = context.resources.displayMetrics
//        val scale = dm.density
//        return dpValue * scale + 0.5f
//    }

//    private fun px2dip(pxValue: Float): Int {
//        val scale = context.resources.displayMetrics.density
//        return (pxValue / scale + 0.5f).toInt()
//    }

    init {
        initialize(attrs)
    }
}
