package com.loitpcore.views.sticker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.loitpcore.R
import com.loitp.core.utilities.LLog
import com.loitp.core.utilities.LUIUtil.Companion.isDarkTheme
import kotlin.math.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class StickerView : FrameLayout {

    companion object {
        const val logTag = "com.knef.stickerView"
        private const val BUTTON_SIZE_DP = 30
        private const val SELF_SIZE_DP = 100
        private fun convertDpToPixel(dp: Float, context: Context): Int {
            val resources = context.resources
            val metrics = resources.displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return px.toInt()
        }
    }

    private var ivBorder: BorderView? = null
    private var ivScale: ImageView? = null
    private var ivDelete: ImageView? = null
    private var ivFlip: ImageView? = null

    // For scalling
    private var thisOrgX = -1f
    private var thisOrgY = -1f
    private var scaleOrgx = -1f
    private var scaleOrgy = -1f
    private var scaleOrgWidth = -1.0
    private var scaleOrgHeight = -1.0

    // For rotating
    private var rotateOrgX = -1f
    private var rotateOrgY = -1f
    private var rotateNewX = -1f
    private var rotateNewY = -1f

    // For moving
    private var moveOrgx = -1f
    private var moveOrgy = -1f
    private var centerX = 0.0
    private var centerY = 0.0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context) {
        ivBorder = BorderView(context)
        ivScale = ImageView(context)
        ivDelete = ImageView(context)
        ivFlip = ImageView(context)

        ivScale?.setImageResource(R.drawable.ic_zoom_out_map_black_48dp)
        ivDelete?.setImageResource(R.drawable.ic_close_black_48dp)
        ivFlip?.setImageResource(R.drawable.ic_autorenew_black_48dp)

        if (isDarkTheme()) {
            ivScale?.setColorFilter(Color.WHITE)
            ivDelete?.setColorFilter(Color.WHITE)
            ivFlip?.setColorFilter(Color.WHITE)
        } else {
            ivScale?.setColorFilter(Color.BLACK)
            ivDelete?.setColorFilter(Color.BLACK)
            ivFlip?.setColorFilter(Color.BLACK)
        }

        this.tag = "DraggableViewGroup"
        ivBorder?.tag = "iv_border"
        ivScale?.tag = "iv_scale"
        ivDelete?.tag = "iv_delete"
        ivFlip?.tag = "iv_flip"

        val margin = convertDpToPixel(dp = BUTTON_SIZE_DP.toFloat(), context = getContext()) / 2
        val size = convertDpToPixel(dp = SELF_SIZE_DP.toFloat(), context = getContext())

        val thisParams = LayoutParams(
            size,
            size
        )
        thisParams.gravity = Gravity.CENTER

        val ivMainParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        ivMainParams.setMargins(margin, margin, margin, margin)

        val ivBorderParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        ivBorderParams.setMargins(margin, margin, margin, margin)

        val ivScaleParams = LayoutParams(
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext()),
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext())
        )
        ivScaleParams.gravity = Gravity.BOTTOM or Gravity.END

        val ivDeleteParams = LayoutParams(
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext()),
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext())
        )
        ivDeleteParams.gravity = Gravity.TOP or Gravity.END

        val ivFlipParams = LayoutParams(
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext()),
            convertDpToPixel(BUTTON_SIZE_DP.toFloat(), getContext())
        )
        ivFlipParams.gravity = Gravity.TOP or Gravity.START

        this.layoutParams = thisParams
        this.addView(mainView, ivMainParams)
        this.addView(ivBorder, ivBorderParams)
        this.addView(ivScale, ivScaleParams)
        this.addView(ivDelete, ivDeleteParams)
        this.addView(ivFlip, ivFlipParams)
        setOnTouchListener(mTouchListener)

        ivScale?.setOnTouchListener(mTouchListener)
        ivDelete?.setOnClickListener {
            if (this@StickerView.parent != null) {
                val myCanvas = this@StickerView.parent as ViewGroup
                myCanvas.removeView(this@StickerView)
            }
        }
        ivFlip?.setOnClickListener {
            val mainView = mainView
            mainView.rotationY = if (mainView.rotationY == -180f) {
                0f
            } else {
                -180f
            }
            mainView.invalidate()
            requestLayout()
        }
    }

    @Suppress("unused")
    val isFlip: Boolean
        get() = mainView.rotationY == -180f

    protected abstract val mainView: View

    @SuppressLint("ClickableViewAccessibility")
    private val mTouchListener = OnTouchListener { view, event ->
        if (view.tag == "DraggableViewGroup") {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
//                    LLog.d(logTag, "sticker view action down")
                    moveOrgx = event.rawX
                    moveOrgy = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
//                    LLog.d(logTag, "sticker view action move")
                    val offsetX = event.rawX - moveOrgx
                    val offsetY = event.rawY - moveOrgy
                    this@StickerView.x = this@StickerView.x + offsetX
                    this@StickerView.y = this@StickerView.y + offsetY
                    moveOrgx = event.rawX
                    moveOrgy = event.rawY
                }
                MotionEvent.ACTION_UP -> {
//                    LLog.d(logTag, "sticker view action up")
                }
            }
        } else if (view.tag == "iv_scale") {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
//                    LLog.d(logTag, "iv_scale action down")
                    thisOrgX = this@StickerView.x
                    thisOrgY = this@StickerView.y
                    scaleOrgx = event.rawX
                    scaleOrgy = event.rawY
                    scaleOrgWidth = this@StickerView.layoutParams.width.toDouble()
                    scaleOrgHeight = this@StickerView.layoutParams.height.toDouble()
                    rotateOrgX = event.rawX
                    rotateOrgY = event.rawY
                    centerX =
                        (this@StickerView.x + (this@StickerView.parent as View).x + this@StickerView.width.toFloat() / 2).toDouble()
                    var result = 0
                    val resourceId =
                        resources.getIdentifier("status_bar_height", "dimen", "android")
                    if (resourceId > 0) {
                        result = resources.getDimensionPixelSize(resourceId)
                    }
                    val statusBarHeight = result.toDouble()
                    centerY = this@StickerView.y +
                            (this@StickerView.parent as View).y +
                            statusBarHeight + this@StickerView.height.toFloat() / 2
                }
                MotionEvent.ACTION_MOVE -> {
//                    LLog.d(logTag, "iv_scale action move")
                    rotateNewX = event.rawX
                    rotateNewY = event.rawY
                    val angleDiff = abs(
                        atan2(
                            y = (event.rawY - scaleOrgy).toDouble(),
                            x = (event.rawX - scaleOrgx).toDouble()
                        ) -
                                atan2(
                                    y = scaleOrgy - centerY,
                                    x = scaleOrgx - centerX
                                )
                    ) * 180 / Math.PI
//                    LLog.d(logTag, "angle_diff: $angleDiff")
                    val length1 =
                        getLength(centerX, centerY, scaleOrgx.toDouble(), scaleOrgy.toDouble())
                    val length2 =
                        getLength(centerX, centerY, event.rawX.toDouble(), event.rawY.toDouble())
                    val size = convertDpToPixel(SELF_SIZE_DP.toFloat(), context)
                    if (length2 > length1 && (angleDiff < 25 || abs(angleDiff - 180) < 25)) {
                        // scale up
                        val offsetX = abs(event.rawX - scaleOrgx).toDouble()
                        val offsetY = abs(event.rawY - scaleOrgy).toDouble()
                        val offset = offsetX.coerceAtLeast(offsetY)
                        this@StickerView.layoutParams.width += offset.toInt()
                        this@StickerView.layoutParams.height += offset.toInt()
                        onScaling(true)
                    } else if (length2 < length1 &&
                        (angleDiff < 25 || abs(angleDiff - 180) < 25) &&
                        this@StickerView.layoutParams.width > size / 2 &&
                        this@StickerView.layoutParams.height > size / 2
                    ) {
                        // scale down
                        val offsetX = abs(event.rawX - scaleOrgx).toDouble()
                        val offsetY = abs(event.rawY - scaleOrgy).toDouble()
                        var offset = max(offsetX, offsetY)
                        offset = offset
                        this@StickerView.layoutParams.width -= offset.toInt()
                        this@StickerView.layoutParams.height -= offset.toInt()
                        onScaling(scaleUp = false)
                    }

                    // rotate
                    val angle = atan2(
                        y = event.rawY - centerY,
                        x = event.rawX - centerX
                    ) * 180 / Math.PI
//                    LLog.d(logTag, "log angle: $angle")

                    // setRotation((float) angle - 45);
                    rotation = angle.toFloat() - 45
//                    LLog.d(logTag, "getRotation(): $rotation")
                    onRotating()
                    rotateOrgX = rotateNewX
                    rotateOrgY = rotateNewY
                    scaleOrgx = event.rawX
                    scaleOrgy = event.rawY
                    postInvalidate()
                    requestLayout()
                }
                MotionEvent.ACTION_UP -> {
                    LLog.d(logTag, "iv_scale action up")
                }
            }
        }
        true
    }

    private fun getLength(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return sqrt((y2 - y1).pow(2.0) + (x2 - x1).pow(2.0))
    }

    @Suppress("unused")
    private fun getRelativePos(absX: Float, absY: Float): FloatArray {
//        LLog.d("ken", "getRelativePos getX:" + (this.parent as View).x)
//        LLog.d("ken", "getRelativePos getY:" + (this.parent as View).y)
        val pos = floatArrayOf(
            absX - (this.parent as View).x,
            absY - (this.parent as View).y
        )
//        LLog.d(logTag, "getRelativePos absY:$absY")
        LLog.d(logTag, "getRelativePos relativeY:" + pos[1])
        return pos
    }

    @Suppress("unused")
    fun setControlItemsHidden(isHidden: Boolean) {
        if (isHidden) {
            ivBorder?.visibility = INVISIBLE
            ivScale?.visibility = INVISIBLE
            ivDelete?.visibility = INVISIBLE
            ivFlip?.visibility = INVISIBLE
        } else {
            ivBorder?.visibility = VISIBLE
            ivScale?.visibility = VISIBLE
            ivDelete?.visibility = VISIBLE
            ivFlip?.visibility = VISIBLE
        }
    }

    protected val imageViewFlip: View?
        get() = ivFlip

    @Suppress("unused")
    private fun onScaling(scaleUp: Boolean) {}
    private fun onRotating() {}

    private class BorderView : View {
        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
        constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
            context,
            attrs,
            defStyle
        )

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            // Draw sticker border
            val params = this.layoutParams as LayoutParams
//            LLog.d(logTag, "params.leftMargin: " + params.leftMargin)
            val border = Rect()
            border.left = this.left - params.leftMargin
            border.top = this.top - params.topMargin
            border.right = this.right - params.rightMargin
            border.bottom = this.bottom - params.bottomMargin
            val borderPaint = Paint()
            borderPaint.strokeWidth = 6f
            if (isDarkTheme()) {
                borderPaint.color = Color.WHITE
            } else {
                borderPaint.color = Color.BLACK
            }
            borderPaint.style = Paint.Style.STROKE
            canvas.drawRect(border, borderPaint)
        }
    }

    @Suppress("unused")
    fun setControlsVisibility(isVisible: Boolean) {
        if (!isVisible) {
            ivBorder?.visibility = GONE
            ivDelete?.visibility = GONE
            ivFlip?.visibility = GONE
            ivScale?.visibility = GONE
        } else {
            ivBorder?.visibility = VISIBLE
            ivDelete?.visibility = VISIBLE
            ivFlip?.visibility = VISIBLE
            ivScale?.visibility = VISIBLE
        }
    }
}
