package com.loitpcore.views.scratchView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.* // ktlint-disable no-wildcard-imports
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.loitpcore.R
import com.loitp.core.utilities.LAppResource.getColor
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LScratchTextView : AppCompatTextView {

    companion object {
        const val STROKE_WIDTH = 12f
        private const val TOUCH_TOLERANCE = 4f
        private fun getTextDimens(text: String, paint: Paint): IntArray {
            val end = text.length
            val bounds = Rect()
            paint.getTextBounds(text, 0, end, bounds)
            val width = bounds.left + bounds.width()
            val height = bounds.bottom + bounds.height()
            return intArrayOf(width, height)
        }
    }

    interface IRevealListener {
        fun onRevealed(tv: LScratchTextView)
        fun onRevealPercentChangedListener(stv: LScratchTextView, percent: Float)
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
    private var mErasePaint: Paint? = null
    private var mGradientBgPaint: Paint? = null

    /**
     * Sample Drawable bitmap having the scratch pattern.
     */
    private var mDrawable: BitmapDrawable? = null

    /**
     * Listener object callback reference to send back the callback when the text has been revealed.
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

    constructor(context: Context, set: AttributeSet?) : super(context, set) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
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
        mErasePaint?.strokeWidth = multiplier * STROKE_WIDTH
    }

    /**
     * Initialises the paint drawing elements.
     */
    private fun init() {
        mTouchPath = Path()
        mErasePaint = Paint()
        mErasePaint?.apply {
            isAntiAlias = true
            isDither = true
            color = -0x10000
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.BEVEL
            strokeCap = Paint.Cap.ROUND
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        setStrokeWidth(6)
        mGradientBgPaint = Paint()
        mErasePath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        val scratchBitmap = BitmapFactory.decodeResource(resources, R.drawable.l_ic_scratch_pattern)
        mDrawable = BitmapDrawable(resources, scratchBitmap)
        mDrawable?.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mScratchBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mScratchBitmap?.let { bm ->
            mCanvas = Canvas(bm)
            val rect = Rect(0, 0, bm.width, bm.height)
            mDrawable?.bounds = rect

            val startGradientColor = getColor(R.color.scratchStartGradient)
            val endGradientColor = getColor(R.color.scratchEndGradient)

            mGradientBgPaint?.shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                startGradientColor, endGradientColor, Shader.TileMode.MIRROR
            )

            mGradientBgPaint?.let { p ->
                mCanvas?.drawRect(rect, p)
                mCanvas?.let { cv ->
                    mDrawable?.draw(cv)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mScratchBitmap?.let { bm ->
            canvas.drawBitmap(bm, 0f, 0f, mBitmapPaint)
            mErasePath?.let { e1 ->
                mErasePaint?.let { e2 ->
                    canvas.drawPath(e1, e2)
                }
            }
        }
    }

    private fun touchStart(x: Float, y: Float) {
        mErasePath?.reset()
        mErasePath?.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mErasePath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
            drawPath()
        }
        mTouchPath?.let {
            it.reset()
            it.addCircle(mX, mY, 30f, Path.Direction.CW)
        }
    }

    private fun drawPath() {
        mErasePath?.let { e1 ->
            mErasePaint?.let { e2 ->
                e1.lineTo(mX, mY)
                // commit the path to our offscreen
                mCanvas?.drawPath(e1, e2)
                // kill this so we don't double draw
                mTouchPath?.reset()
                e1.reset()
                e1.moveTo(mX, mY)
                checkRevealed()
            }
        }
    }

    /**
     * Reveals the hidden text by erasing the scratch area.
     */
    fun reveal() {
        val bounds = getTextBounds(1.5f)
        val left = bounds[0]
        val top = bounds[1]
        val right = bounds[2]
        val bottom = bounds[3]
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mCanvas?.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        checkRevealed()
        invalidate()
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
                drawPath()
                invalidate()
            }
            else -> {
            }
        }
        return true
    }

    val color: Int
        get() = mErasePaint?.color ?: Color.RED

    fun setRevealListener(listener: IRevealListener?) {
        mRevealListener = listener
    }

    val isRevealed: Boolean
        get() = mRevealPercent == 1f

    @SuppressLint("StaticFieldLeak")
    private fun checkRevealed() {
        if (!isRevealed && mRevealListener != null) {
            val bounds = textBounds
            val left = bounds[0]
            val top = bounds[1]
            val width = bounds[2] - left
            val height = bounds[3] - top

            // Do not create multiple calls to compare.
            if (mThreadCount > 1) {
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
                                this@LScratchTextView,
                                percentRevealed
                            )
                        }

                        // if now revealed.
                        if (isRevealed) {
                            mRevealListener?.onRevealed(this@LScratchTextView)
                        }
                    }
                }
            }.execute(left, top, width, height)
        }
    }

    private val textBounds: IntArray
        get() = getTextBounds(1f)

    private fun getTextBounds(scale: Float): IntArray {
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        val vwidth = width
        val vheight = height
        val centerX = vwidth / 2
        val centerY = vheight / 2
        val paint = paint
        val text = text.toString()
        val dimens = getTextDimens(text, paint)
        var width = dimens[0]
        var height = dimens[1]
        val lines = lineCount
        height *= lines
        width /= lines
        var left = 0
        var top = 0
        height = if (height > vheight) {
            vheight - (paddingBottom + paddingTop)
        } else {
            (height * scale).toInt()
        }
        width = if (width > vwidth) {
            vwidth - (paddingLeft + paddingRight)
        } else {
            (width * scale).toInt()
        }
        val gravity = gravity
        when {
            gravity and Gravity.START == Gravity.START -> {
                left = paddingLeft
            }
            gravity and Gravity.END == Gravity.END -> {
                left = vwidth - paddingRight - width
            }
            gravity and Gravity.CENTER_HORIZONTAL == Gravity.CENTER_HORIZONTAL -> {
                left = centerX - width / 2
            }
        }
        when {
            gravity and Gravity.TOP == Gravity.TOP -> {
                top = paddingTop
            }
            gravity and Gravity.BOTTOM == Gravity.BOTTOM -> {
                top = vheight - paddingBottom - height
            }
            gravity and Gravity.CENTER_VERTICAL == Gravity.CENTER_VERTICAL -> {
                top = centerY - height / 2
            }
        }
        return intArrayOf(left, top, left + width, top + height)
    }
}
