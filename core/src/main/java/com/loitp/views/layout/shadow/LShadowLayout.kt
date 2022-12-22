package com.loitp.views.layout.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.loitp.core.utilities.LAppResource
import com.loitp.R
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LShadowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        const val ALL = 0x1111
        const val LEFT = 0x0001
        const val TOP = 0x0010
        const val RIGHT = 0x0100
        const val BOTTOM = 0x1000
        const val SHAPE_RECTANGLE = 0x0001
        const val SHAPE_OVAL = 0x0010
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()
    private var mShadowColor = Color.TRANSPARENT
    private var mShadowRadius = 0f
    private var mShadowDx = 0f
    private var mShadowDy = 0f
    private var mShadowSide = ALL
    private var mShadowShape = SHAPE_RECTANGLE

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val effect = mShadowRadius + dip2px(5f)
        var rectLeft = 0f
        var rectTop = 0f
        var rectRight = this.measuredWidth.toFloat()
        var rectBottom = this.measuredHeight.toFloat()
        var paddingLeft = 0
        var paddingTop = 0
        var paddingRight = 0
        var paddingBottom = 0
        this.width
        if (mShadowSide and LEFT == LEFT) {
            rectLeft = effect
            paddingLeft = effect.toInt()
        }
        if (mShadowSide and TOP == TOP) {
            rectTop = effect
            paddingTop = effect.toInt()
        }
        if (mShadowSide and RIGHT == RIGHT) {
            rectRight = this.measuredWidth - effect
            paddingRight = effect.toInt()
        }
        if (mShadowSide and BOTTOM == BOTTOM) {
            rectBottom = this.measuredHeight - effect
            paddingBottom = effect.toInt()
        }
        if (mShadowDy != 0.0f) {
            rectBottom -= mShadowDy
            paddingBottom += mShadowDy.toInt()
        }
        if (mShadowDx != 0.0f) {
            rectRight -= mShadowDx
            paddingRight += mShadowDx.toInt()
        }
        mRectF.left = rectLeft
        mRectF.top = rectTop
        mRectF.right = rectRight
        mRectF.bottom = rectBottom
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setUpShadowPaint()
        if (mShadowShape == SHAPE_RECTANGLE) {
            canvas.drawRect(mRectF, mPaint)
        } else if (mShadowShape == SHAPE_OVAL) {
            canvas.drawCircle(
                mRectF.centerX(),
                mRectF.centerY(),
                min(a = mRectF.width(), b = mRectF.height()) / 2,
                mPaint
            )
        }
    }

    fun setShadowColor(shadowColor: Int) {
        mShadowColor = shadowColor
        requestLayout()
        postInvalidate()
    }

    fun setShadowRadius(shadowRadius: Float) {
        mShadowRadius = shadowRadius
        requestLayout()
        postInvalidate()
    }

    private fun init(attrs: AttributeSet?) {
        setLayerType(LAYER_TYPE_SOFTWARE, null) // 关闭硬件加速
        setWillNotDraw(false) // 调用此方法后，才会执行 onDraw(Canvas) 方法
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LShadowLayout)
        mShadowColor = typedArray.getColor(
            R.styleable.LShadowLayout_shadowColor,
            LAppResource.getColor(R.color.black)
        )
        mShadowRadius = typedArray.getDimension(R.styleable.LShadowLayout_shadowRadius, dip2px(0f))
        mShadowDx = typedArray.getDimension(R.styleable.LShadowLayout_shadowDx, dip2px(0f))
        mShadowDy = typedArray.getDimension(R.styleable.LShadowLayout_shadowDy, dip2px(0f))
        mShadowSide = typedArray.getInt(R.styleable.LShadowLayout_shadowSide, ALL)
        mShadowShape = typedArray.getInt(R.styleable.LShadowLayout_shadowShape, SHAPE_RECTANGLE)
        typedArray.recycle()
        setUpShadowPaint()
    }

    private fun setUpShadowPaint() {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.color = Color.TRANSPARENT
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor)
    }

    private fun dip2px(dpValue: Float): Float {
        val dm = context.resources.displayMetrics
        val scale = dm.density
        return dpValue * scale + 0.5f
    }

    init {
        init(attrs)
    }
}
