package com.loitp.views.pk.gradientColorPickerBar

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.loitp.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 *
 * Created by wangpeiyuan on 2021/9/4.
 */
class GradientColorPickerBar : View {

    companion object {
//        private const val TAG = "GradientColorPickBar"

        const val HORIZONTAL = 0
        const val VERTICAL = 1

        fun parseColorInt(@ColorInt color: Int): String {
            return String.format("#%06X", 0xFFFFFF and color)
        }
    }

    private var orientation: Int = HORIZONTAL
    private var thumbSize: Float = 40f.dp
    private var thumbCorner: Float = 20f.dp
    private var thumbStrokeColor: Int = Color.WHITE
    private var thumbStrokeWidth: Float = 2f.dp
    private var barHeight: Float = 20f.dp
    private var barCorner: Float = 22f.dp

    private val barRect = RectF()
    private val thumbRect = RectF()

    private val barPaint: Paint by lazy {
        Paint().apply {
            this.isAntiAlias = true
            this.strokeCap = Paint.Cap.ROUND
            this.style = Paint.Style.FILL
        }
    }
    private val thumbPaint: Paint by lazy {
        Paint().apply {
            this.isAntiAlias = true
            this.strokeCap = Paint.Cap.ROUND
        }
    }

    private var colors: IntArray = IntArray(0)
    private var colorPositions: FloatArray = FloatArray(0)
    private var selectedColor: Int = Color.BLACK

    @FloatRange(from = 0.0, to = 1.0)
    private var progress: Float = 0f

    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    private var onChangeListener: OnChangeListener? = null

    constructor(context: Context) : this(context, null)
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GradientColorPickerBar)
        thumbSize = a.getDimension(R.styleable.GradientColorPickerBar_thumbSizeGCP, thumbSize)
        thumbCorner = a.getDimension(R.styleable.GradientColorPickerBar_thumbRadiusGCP, thumbCorner)
        thumbStrokeColor =
            a.getColor(R.styleable.GradientColorPickerBar_thumbStrokeColorGCP, thumbStrokeColor)
        thumbStrokeWidth =
            a.getDimension(R.styleable.GradientColorPickerBar_thumbStrokeWidthGCP, thumbStrokeWidth)
        barHeight = a.getDimension(R.styleable.GradientColorPickerBar_barHeightGCP, barHeight)
        barCorner = a.getDimension(R.styleable.GradientColorPickerBar_barRadiusGCP, barCorner)
        orientation = a.getInt(R.styleable.GradientColorPickerBar_orientationGCP, HORIZONTAL)
        val resIdForColors = a.getResourceId(R.styleable.GradientColorPickerBar_colorEntriesGCP, -1)
        if (resIdForColors != -1) {
            colors = resources.getIntArray(resIdForColors)
        }
        progress = a.getFloat(R.styleable.GradientColorPickerBar_progressGCP, progress)
        a.recycle()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        if (orientation == VERTICAL) {
            val width = (max(totalThumbSize(), barHeight) + paddingStart + paddingEnd).roundToInt()
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
        } else {
            val height =
                (max(totalThumbSize(), barHeight) + paddingTop + paddingBottom).roundToInt()
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            )
        }
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        resetData()
    }

    private fun totalThumbSize() = thumbSize + thumbStrokeWidth

    private fun resetData() {
        barRect.setEmpty()
        updateBarRect()
        updatePaintColor()
        findColorByPercent(progress)
    }

    private fun updateBarRect() {
        if (!barRect.isEmpty) return
        if (orientation == HORIZONTAL) {
            barRect.left = paddingStart.toFloat() + totalThumbSize() / 2f
            barRect.top = height / 2f - barHeight / 2f
            barRect.right = width - paddingEnd - totalThumbSize() / 2f
            barRect.bottom = barRect.top + barHeight
        } else {
            barRect.left = width / 2f - barHeight / 2f
            barRect.top = paddingTop + totalThumbSize() / 2f
            barRect.right = barRect.left + barHeight
            barRect.bottom = height - paddingBottom - totalThumbSize() / 2f
        }
//        Log.d(TAG, "updateBarRect: barRect=${barRect.toShortString()}")
    }

    private fun updatePaintColor() {
        if (!hasEnoughColors()) {
//            Log.w(TAG, "updatePaintColor: colors must > 1")
            return
        }
        colorPositions = FloatArray(colors.size)
        val barLength = if (orientation == HORIZONTAL) barRect.width() else barRect.height()
        val posLength = barLength / (colors.size - 1)
        for (i in colors.indices) {
            colorPositions[i] = (posLength * i) / barLength
        }
//        Log.d(TAG, "updatePaintColor: colorPositions=${colorPositions.contentToString()}")
        barPaint.shader = if (orientation == HORIZONTAL) LinearGradient(
            /* x0 = */ barRect.left,
            /* y0 = */ 0f,
            /* x1 = */ barRect.right,
            /* y1 = */ 0f,
            /* colors = */ colors, /* positions = */ colorPositions,
            /* tile = */ Shader.TileMode.CLAMP
        ) else LinearGradient(
            /* x0 = */ 0f,
            /* y0 = */ barRect.top,
            /* x1 = */ 0f,
            /* y1 = */ barRect.bottom,
            /* colors = */ colors, /* positions = */ colorPositions,
            /* tile = */ Shader.TileMode.CLAMP
        )
    }

    private fun updateThumbRect() {
        if (orientation == HORIZONTAL) {
            val position = barRect.left + barRect.width() * progress
            thumbRect.left = position - thumbSize / 2f
            thumbRect.top = barRect.centerY() - thumbSize / 2
            thumbRect.right = thumbRect.left + thumbSize
            thumbRect.bottom = thumbRect.top + thumbSize
        } else {
            val position = barRect.top + barRect.height() * progress
            thumbRect.left = barRect.centerX() - thumbSize / 2f
            thumbRect.top = position - thumbSize / 2f
            thumbRect.right = thumbRect.left + thumbSize
            thumbRect.bottom = thumbRect.top + thumbSize
        }
//        Log.d(TAG, "updateThumbRect: thumbRect=${thumbRect.toShortString()}")
    }

    private fun hasEnoughColors(): Boolean = colors.size > 1

    override fun onDraw(canvas: Canvas) {
        if (!hasEnoughColors()) return
        drawBar(canvas)
        drawThumb(canvas)
    }

    private fun drawBar(canvas: Canvas) {
        //绘制进度条
        updateBarRect()
        canvas.drawRoundRect(barRect, barCorner, barCorner, barPaint)
    }

    private fun drawThumb(canvas: Canvas) {
        //绘制滑块
        updateThumbRect()
        //绘制滑块颜色
        thumbPaint.clearShadowLayer()
        thumbPaint.style = Paint.Style.FILL
        thumbPaint.color = selectedColor
        canvas.drawRoundRect(thumbRect, thumbCorner, thumbCorner, thumbPaint)
        if (thumbStrokeWidth > 0) {
            //绘制滑块边缘
            thumbPaint.style = Paint.Style.STROKE
            thumbPaint.color = thumbStrokeColor
            thumbPaint.strokeWidth = thumbStrokeWidth
            canvas.drawRoundRect(thumbRect, thumbCorner, thumbCorner, thumbPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (canHandlerGesture() && isEnabled) {
            gestureDetector.onTouchEvent(event)
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                onChangeListener?.onStartTrackingTouch(this)
            } else if (event.actionMasked == MotionEvent.ACTION_UP ||
                event.actionMasked == MotionEvent.ACTION_CANCEL
            ) {
                onChangeListener?.onStopTrackingTouch(this)
            }
            return true
        }
        return false
    }

    private fun canHandlerGesture(): Boolean = hasEnoughColors() && !barRect.isEmpty

    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(context, onGestureListener, null, true)
    }
    private val onGestureListener: GestureDetector.OnGestureListener =
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                calculateProgress(if (orientation == HORIZONTAL) e.x else e.y)
                return super.onDown(e)
            }

            override fun onScroll(
                e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float
            ): Boolean {
                calculateProgress(if (orientation == HORIZONTAL) e2.x else e2.y)
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        }

    private fun calculateProgress(point: Float) {
        progress = if (orientation == HORIZONTAL) {
            val positionX = min(max(barRect.left, point), barRect.right)
            (positionX - barRect.left) / barRect.width()
        } else {
            val positionY = min(max(barRect.top, point), barRect.bottom)
            (positionY - barRect.top) / barRect.height()
        }
        findColorByPercent(progress)
//        Log.d(TAG, "calculateProgress: progress=$progress")
        onChangeListener?.onProgressChanged(
            gradientColorPickBar = this,
            progress = progress,
            color = selectedColor,
            fromUser = true
        )
        postInvalidate()
    }

    private fun findColorByPercent(@FloatRange(from = 0.0, to = 1.0) percent: Float) {
        if (!barRect.isEmpty && colors.isNotEmpty() && colorPositions.isNotEmpty()) {
//            Log.d(TAG, "findColorByPercent: percent=$percent")
            if (percent == 0f) {
                selectedColor = colors.first()
            } else if (percent == 1.0f) {
                selectedColor = colors.last()
            } else {
                var preColor = colors[0]
                var nextColor = colors[1]
                var index = 0
                for (i in colorPositions.indices) {
                    if (percent <= colorPositions[i]) {
                        index = i
                        preColor = colors[i - 1]
                        nextColor = colors[i]
                        break
                    }
                }
                val totalLength =
                    if (orientation == HORIZONTAL) barRect.width() else barRect.height()
                val percentLength =
                    percent * totalLength - (colorPositions[index - 1] * totalLength)
                val colorLength = colorPositions[1] * totalLength
                val fraction = percentLength / colorLength
//                Log.d(TAG, "findColorByPercent: percentLength=$percentLength,colorLength=$colorLength,fraction$fraction")
                selectedColor = argbEvaluator.evaluate(fraction, preColor, nextColor) as Int
            }
//            Log.d(TAG, "findColorByPercent: selectedColor=${parseColorInt(selectedColor)}")
        }
    }

    fun setOrientation(orientation: Int) {
        if (this.orientation == orientation || (orientation != HORIZONTAL && orientation != VERTICAL)) return
        this.orientation = orientation
        requestLayout()
    }

    fun getOrientation() = orientation

    fun setThumbStyle(
        thumbSize: Float,
        thumbRadius: Float,
        @ColorInt thumbStrokeColor: Int,
        thumbStrokeWidth: Float
    ) {
        this.thumbSize = thumbSize
        this.thumbCorner = thumbRadius
        this.thumbStrokeColor = thumbStrokeColor
        this.thumbStrokeWidth = thumbStrokeWidth
        requestLayout()
    }

    fun setBarStyle(
        barHeight: Float,
        barRadius: Float
    ) {
        this.barHeight = barHeight
        this.barCorner = barRadius
        requestLayout()
    }

    fun setColors(colors: IntArray) {
//        if (!hasEnoughColors()) {
//            Log.w(TAG, "updatePaintColor: colors must > 1")
//        }
        this.colors = colors
        updatePaintColor()
        setProgress(0f)
    }

    @Suppress("unused")
    @FloatRange(from = 0.0, to = 1.0)
    fun getProgress() = progress

    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        this.progress = progress
        if (!hasEnoughColors()) return
        findColorByPercent(progress)
        onChangeListener?.onProgressChanged(this, progress, selectedColor, false)
        postInvalidate()
    }

    @Suppress("unused")
    fun getSelectedColor() = selectedColor

    fun setOnChangeListener(onChangeListener: OnChangeListener?) {
        this.onChangeListener = onChangeListener
    }

    interface OnChangeListener {
        fun onProgressChanged(
            gradientColorPickBar: GradientColorPickerBar,
            progress: Float,
            color: Int,
            fromUser: Boolean
        )

        fun onStartTrackingTouch(gradientColorPickBar: GradientColorPickerBar) = Unit

        fun onStopTrackingTouch(gradientColorPickBar: GradientColorPickerBar) = Unit
    }
}

private val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
