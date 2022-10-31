package com.loitpcore.views.imageView.fidgetSpinner

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import com.loitpcore.core.utilities.LAppResource
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LFidgetSpinner : FrameLayout {

    companion object {
        private const val MAX_ACTION_UP_SPEED = 20
        private const val MIN_DURATION = 500
        private const val MAX_DURATION = 8000
    }

    private var isPivot = false
    private var xActionDown = 0f
    private var yActionDown = 0f
    private var centerX = 0f
    private var centerY = 0f
    private var actionUpSpeed = 0.0
    private var currentSpeed = 0f
        set(currentSpeed) {
            val now = System.currentTimeMillis()
            val timeDiff = now - spanAnimationFrameTime.toFloat()
            currentAngle += currentSpeed * timeDiff * 0.5f
            currentAngleTime = now
            field = currentSpeed
            spanAnimationFrameTime = now
        }
    private var fidgetSpinnerImageView: ImageView? = null
    private var currentAngleTime: Long = 0
    private var lastAngleTime: Long = 0
    private var currentAngle = 0.0
    private var offsetAngle = 0.0
    private var lastAngle = 0.0
    private var spinAnimation: ObjectAnimator? = null
    private var spanAnimationFrameTime: Long = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    fun setImageDrawable(id: Int) {
//        fidgetSpinnerImageView?.setImageDrawable(context.resources.getDrawable(id))
        fidgetSpinnerImageView?.setImageDrawable(LAppResource.getDrawable(id))
    }

    private fun onActionMove(x: Float, y: Float) {
        if (!isPivot) {
            isPivot = true
            fidgetSpinnerImageView?.let {
                centerX = it.width / 2.toFloat()
                centerY = it.height / 2.toFloat()
                it.pivotX = centerX
                it.pivotY = centerY
            }
        }
        val angle = getRotationAngle(x, y)
        rotate(angle.toFloat())
    }

    private fun getRotationAngle(x: Float, y: Float): Double {
        lastAngle = currentAngle
        lastAngleTime = currentAngleTime
        val now = System.currentTimeMillis()
        val dx1 = centerX - x
        val dy1 = centerY - y
        val dx2 = centerX - xActionDown
        val dy2 = centerY - yActionDown
        val alpha1 = adjustAlpha(Math.toDegrees(atan(dx1 / dy1.toDouble())), dx1, dy1)
        val alpha2 = adjustAlpha(Math.toDegrees(atan(dx2 / dy2.toDouble())), dx2, dy2)
        currentAngle = (alpha1 - alpha2 + offsetAngle) % 360
        currentAngleTime = now
        return currentAngle
    }

    @Suppress("unused")
    private fun adjustAlpha(alpha: Double, x: Float, y: Float): Double {
        if (y > 0) return -alpha // Top
        return if (y < 0) 180 - alpha else alpha // Bottom
    }

    private fun onActionUp(x: Float, y: Float) {
        offsetAngle = currentAngle
        actionUpSpeed = min(MAX_ACTION_UP_SPEED.toDouble(), calcActionUpSpeed())
        startFidgetSpinnerAnimation()
    }

    private fun startFidgetSpinnerAnimation() {
        spanAnimationFrameTime = System.currentTimeMillis()
        currentSpeed = actionUpSpeed.toFloat()
        val start = actionUpSpeed.toFloat()
        val end = 0f
        val duration = max(
            MIN_DURATION.toLong(),
            min(MAX_DURATION.toLong(), abs((actionUpSpeed * 1000).toLong()))
        )
        spinAnimation = ObjectAnimator.ofFloat(this, "currentSpeed", start, end)
        spinAnimation?.let {
            it.interpolator = AccelerateDecelerateInterpolator()
            it.duration = duration
            it.startDelay = 0
            it.addUpdateListener { rotate(currentAngle.toFloat()) }
            it.start()
        }
    }

    private fun onActionDown(x: Float, y: Float) {
        try {
            spinAnimation?.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        currentAngleTime = System.currentTimeMillis()
        lastAngleTime = currentAngleTime
        lastAngle = currentAngle
        xActionDown = x
        yActionDown = y
    }

    private fun rotate(rotation: Float) {
        fidgetSpinnerImageView?.rotation = rotation
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        fidgetSpinnerImageView = ImageView(context)
        fidgetSpinnerImageView?.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        addView(fidgetSpinnerImageView)
        val onTouchListener = OnTouchListener { _, event -> // Only the first pointer is used
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> onActionDown(x, y)
                MotionEvent.ACTION_UP -> onActionUp(x, y)
                MotionEvent.ACTION_MOVE -> onActionMove(x, y)
                else -> {
                }
            }
            true
        }
        setOnTouchListener(onTouchListener)
    }

    @Suppress("unused")
    fun calcActionUpSpeed(): Double {
        val angleTimeDiff = currentAngleTime - lastAngleTime.toFloat()
        val angleDiff = currentAngle - lastAngle
        return angleDiff / angleTimeDiff
    }
}
