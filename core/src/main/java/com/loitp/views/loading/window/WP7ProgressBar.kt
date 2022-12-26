package com.loitp.views.loading.window

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.loitp.R
import com.loitp.core.utilities.LAppResource.getColor

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class WP7ProgressBar : LinearLayout {

    companion object {
        private const val INTERVAL_DEF = 150
        private const val INDICATOR_COUNT_DEF = 5
        private const val ANIMATION_DURATION_DEF = 2200
        private const val INDICATOR_HEIGHT_DEF = 5
        private const val INDICATOR_RADIUS_DEF = 0
    }

    private var interval = 0
    private var animationDuration = 0
    private var indicatorHeight = 0
    private var indicatorColor = 0
    private var indicatorRadius = 0
    private var isShowing = false
    private var listWP7Indicators: ArrayList<WP7Indicator>? = null
    private var mHandler: Handler? = null
    private var progressBarCount = 0
    private var objectAnimator: ObjectAnimator? = null

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        this.gravity = Gravity.CENTER
        this.orientation = HORIZONTAL
        mHandler = Handler(Looper.getMainLooper())
        setAttributes(attrs)
        initializeIndicators()
    }

    private fun setAttributes(attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WP7ProgressBar)
        interval = typedArray.getInt(R.styleable.WP7ProgressBar_wpInterval, INTERVAL_DEF)
        animationDuration = typedArray.getInt(
            R.styleable.WP7ProgressBar_wpAnimationDuration,
            ANIMATION_DURATION_DEF
        )
        indicatorHeight =
            typedArray.getInt(R.styleable.WP7ProgressBar_wpIndicatorHeight, INDICATOR_HEIGHT_DEF)
        indicatorRadius =
            typedArray.getInt(R.styleable.WP7ProgressBar_wpIndicatorRadius, INDICATOR_RADIUS_DEF)
        indicatorColor = typedArray.getColor(
            R.styleable.WP7ProgressBar_wpIndicatorColor,
            getColor(R.color.colorAccent)
        )
        typedArray.recycle()
    }

    private fun showAnimation() {
        listWP7Indicators?.let {
            for (i in it.indices) {
                it[i].startAnim(
                    animationDuration = animationDuration.toLong(),
                    delay = ((5 - i) * interval).toLong()
                )
            }
        }
    }

    private fun initializeIndicators() {
        removeAllViews()
        val list = ArrayList<WP7Indicator>()
        for (i in 0 until INDICATOR_COUNT_DEF) {
            val wP7Indicator = WP7Indicator(
                context = context,
                indicatorHeight = indicatorHeight,
                color = indicatorColor,
                radius = indicatorRadius
            )
            list.add(wP7Indicator)
            this.addView(wP7Indicator)
        }
        listWP7Indicators = list
    }

    private fun show() {
        if (isShowing) {
            return
        }
        isShowing = true
        showAnimation()
    }

    private fun hide() {
        clearIndicatorsAnimations()
        isShowing = false
    }

    @Suppress("unused")
    private fun startWholeViewAnimation() {
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", -200f, 200f)
        objectAnimator?.apply {
            interpolator = LinearInterpolator()
            duration = (animationDuration + 5 * interval).toLong()
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            start()
        }
    }

    @Suppress("unused")
    private fun hideWholeViewAnimation() {
        objectAnimator?.apply {
            removeAllListeners()
            cancel()
            end()
        }
    }

    private fun clearIndicatorsAnimations() {
        listWP7Indicators?.forEach {
            it.removeAnim()
        }
    }

    fun showProgressBar() {
        progressBarCount++
        if (progressBarCount == 1) {
            mHandler?.post {
                show()
            }
        }
    }

    fun hideProgressBar() {
        progressBarCount--
        mHandler?.postDelayed({
            if (progressBarCount == 0) {
                hide()
            }
        }, 50)
    }

    @Suppress("unused")
    fun setInterval(interval: Int) {
        this.interval = interval
        initializeIndicators()
    }

    @Suppress("unused")
    fun setAnimationDuration(animationDuration: Int) {
        this.animationDuration = animationDuration
        initializeIndicators()
    }

    @Suppress("unused")
    fun setIndicatorHeight(indicatorHeight: Int) {
        this.indicatorHeight = indicatorHeight
        initializeIndicators()
    }

    @Suppress("unused")
    fun setIndicatorColor(indicatorColor: Int) {
        this.indicatorColor = indicatorColor
        initializeIndicators()
    }

    @Suppress("unused")
    fun setIndicatorRadius(indicatorRadius: Int) {
        this.indicatorRadius = indicatorRadius
        initializeIndicators()
    }
}
