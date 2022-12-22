package com.loitp.views.scratch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.loitp.core.utilities.LAppResource.getColor
import com.loitpcore.R
import timber.log.Timber
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LScratchImageView : AppCompatImageView {

    companion object {
        const val STROKE_WIDTH = 12f
        private const val TOUCH_TOLERANCE = 4f
    }

    interface IRevealListener {
        fun onRevealed(iv: LScratchImageView)
        fun onRevealPercentChangedListener(siv: LScratchImageView, percent: Float)
    }

    private var mX = 0f
    private var mY = 0f

    /**
     * Bitmap holding the scratch region.
     */
    private var mScratchBitmap: Bitmap? = null

    /**
     * Drawable canvas area through which the scratchable area is drawn.
     */
    private var mCanvas: Canvas? = null

    /**
     * Path holding the erasing path done by the user.
     */
    private var mErasePath: Path? = null

    /**
     * Path to indicate where the user have touched.
     */
    private var mTouchPath: Path? = null

    /**
     * Paint properties for drawing the scratch area.
     */
    private var mBitmapPaint: Paint? = null

    /**
     * Paint properties for erasing the scratch region.
     */
    var erasePaint: Paint? = null
        private set
    private var mGradientBgPaint: Paint? = null

    /**
     * Sample Drawable bitmap having the scratch pattern.
     */
    private var mDrawable: BitmapDrawable? = null

    /**
     * Listener object callback reference to send back the callback when the image has been revealed.
     */
    private var mRevealListener: IRevealListener? = null

    /**
     * Reveal percent value.
     */
    private var mRevealPercent = 0f

    /**
     * Thread Count
     */
    private var mThreadCount = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        set: AttributeSet?
    ) : super(context, set) {
        init()
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
        init()
    }

    /**
     * Set the strokes width based on the parameter multiplier.
     *
     * @param multiplier can be 1,2,3 and so on to set the stroke width of the paint.
     */
    fun setStrokeWidth(multiplier: Int) {
        erasePaint?.strokeWidth = multiplier * STROKE_WIDTH
    }

    /**
     * Initialises the paint drawing elements.
     */
    private fun init() {
        mTouchPath = Path()
        erasePaint = Paint()
        erasePaint?.apply {
            isAntiAlias = true
            isDither = true
            color = -0x10000
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.BEVEL
            strokeCap = Paint.Cap.ROUND
        }

        setStrokeWidth(6)
        mGradientBgPaint = Paint()
        mErasePath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        val scratchBitmap = BitmapFactory.decodeResource(resources, R.drawable.l_ic_scratch_pattern)
        mDrawable = BitmapDrawable(resources, scratchBitmap)
        mDrawable?.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        setEraserMode()
    }

    override fun onSizeChanged(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        super.onSizeChanged(w, h, oldw, oldh)

        mScratchBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mScratchBitmap?.let {
            mCanvas = Canvas(it)
            val rect = Rect(0, 0, it.width, it.height)
            mDrawable?.bounds = rect

            val startGradientColor = getColor(R.color.scratchStartGradient)
            val endGradientColor = getColor(R.color.scratchEndGradient)
            mGradientBgPaint?.shader = LinearGradient(
                0f,
                0f,
                0f,
                height.toFloat(),
                startGradientColor,
                endGradientColor,
                Shader.TileMode.MIRROR
            )

            mGradientBgPaint?.let { p ->
                mCanvas?.drawRect(rect, p)
            }
            mCanvas?.let { cv ->
                mDrawable?.draw(cv)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mScratchBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, mBitmapPaint)
        }
        mErasePath?.let { e1 ->
            erasePaint?.let { e2 ->
                canvas.drawPath(e1, e2)
            }
        }
    }

    private fun touchStart(
        x: Float,
        y: Float
    ) {
        mErasePath?.reset()
        mErasePath?.moveTo(x, y)
        mX = x
        mY = y
    }

    /**
     * clears the scratch area to reveal the hidden image.
     */
    fun clear() {
        val bounds = imageBounds
        var left = bounds[0]
        var top = bounds[1]
        var right = bounds[2]
        var bottom = bounds[3]
        val width = right - left
        val height = bottom - top
        val centerX = left + width / 2
        val centerY = top + height / 2
        left = centerX - width / 2
        top = centerY - height / 2
        right = left + width
        bottom = top + height
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mCanvas?.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        checkRevealed()
        invalidate()
    }

    private fun touchMove(
        x: Float,
        y: Float
    ) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mErasePath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
            drawPath()
        }
        mTouchPath?.reset()
        mTouchPath?.addCircle(mX, mY, 30f, Path.Direction.CW)
    }

    private fun drawPath() {
        mErasePath?.lineTo(mX, mY)
        // commit the path to our offscreen

        mErasePath?.let { e1 ->
            erasePaint?.let { e2 ->
                mCanvas?.drawPath(e1, e2)
            }
        }

        // kill this so we don't double draw
        mTouchPath?.reset()
        mErasePath?.reset()
        mErasePath?.moveTo(mX, mY)
        checkRevealed()
    }

    fun reveal() {
        clear()
    }

    private fun touchUp() {
        drawPath()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x = x, y = y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x = x, y = y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
            else -> {
            }
        }
        return true
    }

    val color: Int
        get() = erasePaint?.color ?: Color.RED

    fun setEraserMode() {
        erasePaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun setRevealListener(listener: IRevealListener?) {
        mRevealListener = listener
    }

    val isRevealed: Boolean
        get() = mRevealPercent == 1f

    @SuppressLint("StaticFieldLeak")
    private fun checkRevealed() {
        if (!isRevealed && mRevealListener != null) {
            val bounds = imageBounds
            val left = bounds[0]
            val top = bounds[1]
            val width = bounds[2] - left
            val height = bounds[3] - top

            // Do not create multiple calls to compare.
            if (mThreadCount > 1) {
                Timber.tag("Captcha").d("Count greater than 1")
                return
            }
            mThreadCount++

            object : AsyncTask<Int, Void, Float>() {

                @Deprecated("Deprecated in Java")
                @Suppress("NAME_SHADOWING")
                override fun doInBackground(vararg params: Int?): Float {
                    return try {
                        val left = params[0] ?: 0
                        val top = params[1] ?: 0
                        val width = params[2] ?: 0
                        val height = params[3] ?: 0
                        val croppedBitmap =
                            Bitmap.createBitmap(mScratchBitmap!!, left, top, width, height)
                        BitmapUtils.getTransparentPixelPercent(croppedBitmap)
                    } finally {
                        mThreadCount--
                    }
                }

                @Deprecated("Deprecated in Java")
                public override fun onPostExecute(percentRevealed: Float) {

                    // check if not revealed before.
                    if (!isRevealed) {
                        val oldValue = mRevealPercent
                        mRevealPercent = percentRevealed
                        if (oldValue != percentRevealed) {
                            mRevealListener?.onRevealPercentChangedListener(
                                this@LScratchImageView,
                                percentRevealed
                            )
                        }

                        // if now revealed.
                        if (isRevealed) {
                            mRevealListener?.onRevealed(this@LScratchImageView)
                        }
                    }
                }
            }.execute(left, top, width, height)
        }
    }

    private val imageBounds: IntArray
        get() {
            val paddingLeft = paddingLeft
            val paddingTop = paddingTop
            val paddingRight = paddingRight
            val paddingBottom = paddingBottom
            val vwidth = width - paddingLeft - paddingRight
            val vheight = height - paddingBottom - paddingTop
            val centerX = vwidth / 2
            val centerY = vheight / 2
            val drawable = drawable
            val bounds = drawable.bounds
            var width = drawable.intrinsicWidth
            var height = drawable.intrinsicHeight
            if (width <= 0) {
                width = bounds.right - bounds.left
            }
            if (height <= 0) {
                height = bounds.bottom - bounds.top
            }
            val left: Int
            val top: Int
            if (height > vheight) {
                height = vheight
            }
            if (width > vwidth) {
                width = vwidth
            }
            when (scaleType) {
                ScaleType.FIT_START -> {
                    left = paddingLeft
                    top = centerY - height / 2
                }
                ScaleType.FIT_END -> {
                    left = vwidth - paddingRight - width
                    top = centerY - height / 2
                }
                ScaleType.CENTER -> {
                    left = centerX - width / 2
                    top = centerY - height / 2
                }
                else -> {
                    left = paddingLeft
                    top = paddingTop
                    width = vwidth
                    height = vheight
                }
            }
            return intArrayOf(left, top, left + width, top + height)
        }
}
