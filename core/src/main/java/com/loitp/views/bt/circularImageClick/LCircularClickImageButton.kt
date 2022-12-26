package com.loitp.views.bt.circularImageClick

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LCircularClickImageButton : AppCompatImageButton {

    /**
     * Interface definition for a callback to be invoked when a circle view is clicked
     */
    interface OnCircleClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        fun onClick(v: View?)
    }

    // center x of Image
    private var centerX = 0f

    // center y of Image
    private var centerY = 0f

    // the radius of circle
    private var radius = 0f

    // the circle view on click listener
    private var onCircleClickListener: OnCircleClickListener? = null

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param onCircleClickListener The callback that will run
     */
    fun setOnCircleClickListener(onCircleClickListener: OnCircleClickListener?) {
        if (!isClickable) {
            isClickable = true
        }
        this.onCircleClickListener = onCircleClickListener
    }

    override fun onSizeChanged(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w / 2.toFloat()
        centerX = radius
        centerY = h / 2.toFloat()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        // here we get the toucj event and then we make sure that the touch event
        // was inside the circle not outside it if so invoke the call back
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val eventX = event.x
                val eventY = event.y
                if (eventY == centerY) {
                    if (abs(eventX - centerX) <= radius) {
                        onCircleClickListener?.onClick(this)
                        return super.onTouchEvent(event)
                    }
                } else {
                    val firstLeg = abs(eventX - centerX).toDouble().pow(2.0)
                    val secLeg = abs(eventY - centerY).toDouble().pow(2.0)
                    val hypotenuse = sqrt(firstLeg + secLeg)

                    if (hypotenuse <= radius) {
                        onCircleClickListener?.onClick(this)
                        return super.onTouchEvent(event)
                    }
                }
            }
            MotionEvent.ACTION_UP -> return super.onTouchEvent(event)
        }
        return false
    }
}
