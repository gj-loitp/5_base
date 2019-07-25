package com.views.button.roundedbutton

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Property
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import loitp.core.R
import kotlin.math.pow
import kotlin.math.sqrt


class LRoundedButton(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    //attrs
    private val rectF = RectF()
    private val paint = Paint()

    private val buttonCornerRadius: Float
    private val buttonBackgroundColor: Int

    private val buttonGradientStartColor: Int
    private val buttonGradientEndColor: Int

    //ripple
    private val currentCoords = Point()
    private val previousCoords = Point()
    private val ripplePaint = Paint()
    private val rippleAnimator: AnimatorSet = AnimatorSet()
    private val fadeAnimator: AnimatorSet = AnimatorSet()
    private val hoverAnimator: AnimatorSet = AnimatorSet()

    private var rippleColor: Int
    private var rippleRadius = 0f
    private var rippleAlpha = 100

    private val mPath = Path()


    private var radiusProperty = object : Property<LRoundedButton, Float>(Float::class.java, "radius") {
        override fun get(buttonL: LRoundedButton?): Float {
            return getRippleRadius()
        }

        override fun set(buttonL: LRoundedButton?, value: Float?) {
            setRippleRadius(value ?: 0f)
        }
    }


    private var circleAlphaProperty = object : Property<LRoundedButton, Int>(Int::class.java, "rippleAlpha") {
        override fun get(buttonL: LRoundedButton?): Int? {
            return getRippleAlpha()
        }

        override fun set(buttonL: LRoundedButton?, value: Int?) {
            setRippleAlpha(value ?: 100)
        }
    }

    init {
        isClickable = true

        val styledAttrs = getContext().theme
                .obtainStyledAttributes(attrs, R.styleable.LRoundedButton, 0, 0)

        buttonCornerRadius = styledAttrs.getDimension(R.styleable.LRoundedButton_buttonCornerRadius, 0f)
        buttonBackgroundColor = styledAttrs
                .getColor(
                        R.styleable.LRoundedButton_buttonColor,
                        ContextCompat.getColor(getContext(), R.color.colorPrimary)
                )

        buttonGradientStartColor = styledAttrs
                .getColor(R.styleable.LRoundedButton_buttonGradientStartColor, -1)

        buttonGradientEndColor = styledAttrs
                .getColor(R.styleable.LRoundedButton_buttonGradientEndColor, -1)

        //ripple
        rippleColor = styledAttrs.getColor(R.styleable.LRoundedButton_buttonRippleColor,
                Color.WHITE)
        rippleAlpha = styledAttrs.getInt(R.styleable.LRoundedButton_buttonRippleAlpha, 100)

        initPaint()
        initRipplePaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0
                && h > 0
                && buttonGradientStartColor != -1
                && buttonGradientEndColor != -1
        )
            setGradient(w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        val offset = 0f
        val radius = buttonCornerRadius

        rectF.set(offset, offset, width.toFloat() - offset, height.toFloat() - offset)

        mPath.rewind()
        mPath.addRoundRect(rectF, buttonCornerRadius,
                buttonCornerRadius, Path.Direction.CCW)
        canvas.clipPath(mPath)

        canvas.drawRoundRect(rectF, radius, radius, paint)

        super.onDraw(canvas)

        //ripple
        canvas.drawCircle(currentCoords.x.toFloat(), currentCoords.y.toFloat(), getRippleRadius(), ripplePaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        when (event.action) {
            ACTION_DOWN -> {
                previousCoords.set(currentCoords.x, currentCoords.y)
                currentCoords.set(event.x.toInt(), event.y.toInt())
                startHover()
            }
            ACTION_UP -> {
                val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
                val isEventInBounds = bounds.contains((event.x.toInt()).toFloat(), (event.y.toInt()).toFloat())
                if (ripplePaint.alpha != 0 && isEventInBounds) startRipple()
            }
            ACTION_MOVE -> {
                val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
                val isEventInBounds = bounds.contains((event.x.toInt()).toFloat(), (event.y.toInt()).toFloat())
                if (!isEventInBounds && ripplePaint.alpha != 0) fade()
            }
        }
        return super.onTouchEvent(event)
    }


    private fun initPaint() {
        paint.style = Paint.Style.FILL
        paint.color = buttonBackgroundColor
        paint.isAntiAlias = true
    }

    private fun initRipplePaint() {
        ripplePaint.isAntiAlias = true
        ripplePaint.color = rippleColor
        ripplePaint.alpha = rippleAlpha
    }

    private fun startRipple() {
        val endRadius = getEndRadius()

        cancelAllAnimations()

        val ripple = ObjectAnimator.ofFloat(this, radiusProperty, rippleRadius, endRadius)
        ripple.duration = 200
        ripple.interpolator = DecelerateInterpolator()

        val fade = getFadeRippleObjectAnimator(ripple.duration)

        rippleAnimator.playTogether(ripple, fade)
        rippleAnimator.start()
    }

    private fun startHover() {
        val endRadius = getEndRadius()
        cancelAllAnimations()

        setRippleRadius(0f)
        setRippleAlpha(100)

        val ripple = ObjectAnimator.ofFloat(this, radiusProperty, rippleRadius, endRadius)

        ripple.duration = 200
        ripple.interpolator = DecelerateInterpolator()
        hoverAnimator.play(ripple)
        hoverAnimator.start()
    }

    private fun getRippleRadius(): Float {
        return rippleRadius
    }

    private fun setRippleRadius(radius: Float) {
        this.rippleRadius = radius
        invalidate()
    }

    private fun getRippleAlpha(): Int {
        return ripplePaint.alpha
    }

    private fun setRippleAlpha(rippleAlpha: Int) {
        ripplePaint.alpha = rippleAlpha
        invalidate()
    }


    private fun getEndRadius(): Float {
        val halfWidth = width / 2
        val halfHeight = height / 2

        val radiusX = if (halfWidth > currentCoords.x) width - currentCoords.x else currentCoords.x
        val radiusY = if (halfHeight > currentCoords.y) height - currentCoords.y else currentCoords.y

        return sqrt(radiusX.toDouble().pow(2.0) + radiusY.toDouble().pow(2.0)).toFloat() * 1.2f
    }

    private fun fade() {
        fadeAnimator.play(getFadeRippleObjectAnimator())
        fadeAnimator.start()
    }

    private fun getFadeRippleObjectAnimator(delay: Long = 0) =
            ObjectAnimator.ofInt(this, circleAlphaProperty, rippleAlpha, 0)
                    .apply {
                        duration = 75
                        interpolator = AccelerateInterpolator()
                        startDelay = delay
                    }


    private fun setGradient(width: Float, height: Float) {
        paint.shader = LinearGradient(
                0f,
                0f,
                width,
                height,
                buttonGradientStartColor,
                buttonGradientEndColor,
                Shader.TileMode.REPEAT
        )

        invalidate()
    }

    private fun cancelAllAnimations() {
        rippleAnimator.cancel()
        rippleAnimator.removeAllListeners()

        hoverAnimator.cancel()

        fadeAnimator.cancel()
    }

}