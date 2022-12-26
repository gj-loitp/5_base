package com.loitp.picker.shiftColor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.loitp.R
import kotlin.math.roundToInt

class LineColorPicker(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

    var colors: IntArray = if (isInEditMode) {
        Palette.DEFAULT
    } else {
        IntArray(1)
    }
    private val paint: Paint = Paint()
    private val rect = Rect()

    @Suppress("unused")
    var isColorSelected = false

    /**
     * Return currently selected color.
     */
    var color = colors[0]
        private set
    private var onColorChanged: OnColorChangedListener? = null
    private var cellSize = 0
    private var mOrientation = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mOrientation == HORIZONTAL) {
            drawHorizontalPicker(canvas)
        } else {
            drawVerticalPicker(canvas)
        }
    }

    private fun drawVerticalPicker(canvas: Canvas) {
        rect.left = 0
        rect.top = 0
        rect.right = canvas.width
        rect.bottom = 0

        val margin = (canvas.width * 0.08f).roundToInt()
        for (color in colors) {
            paint.color = color
            rect.top = rect.bottom
            rect.bottom += cellSize
            if (isColorSelected && color == this.color) {
                rect.left = 0
                rect.right = canvas.width
            } else {
                rect.left = margin
                rect.right = canvas.width - margin
            }
            canvas.drawRect(rect, paint)
        }
    }

    private fun drawHorizontalPicker(canvas: Canvas) {
        rect.left = 0
        rect.top = 0
        rect.right = 0
        rect.bottom = canvas.height

        // 8%
        val margin = (canvas.height * 0.08f).roundToInt()
        for (color in colors) {
            paint.color = color
            rect.left = rect.right
            rect.right += cellSize
            if (isColorSelected && color == this.color) {
                rect.top = 0
                rect.bottom = canvas.height
            } else {
                rect.top = margin
                rect.bottom = canvas.height - margin
            }
            canvas.drawRect(rect, paint)
        }
    }

    private fun onColorChanged(color: Int) {
        onColorChanged?.onColorChanged(color)
    }

    private var isClick = false
    private var screenW = 0
    private var screenH = 0

    init {
        paint.style = Paint.Style.FILL
        val a = context.theme.obtainStyledAttributes(
            /* set = */ attrs,
            /* attrs = */R.styleable.LineColorPicker,
            /* defStyleAttr = */0,
            /* defStyleRes = */0
        )
        try {
            mOrientation = a.getInteger(R.styleable.LineColorPicker_lcpOrientation, HORIZONTAL)
            if (!isInEditMode) {
                val colorsArrayResId = a.getResourceId(R.styleable.LineColorPicker_lcpColors, -1)
                if (colorsArrayResId > 0) {
                    val colors = context.resources.getIntArray(colorsArrayResId)
                    setColorArray(colors)
                }
            }
            val selected = a.getInteger(R.styleable.LineColorPicker_lcpSelectedColorIndex, -1)
            if (selected != -1) {
                val currentColors = colors
                val currentColorsLength = currentColors.size
                if (selected < currentColorsLength) {
                    setSelectedColorPosition(selected)
                }
            }
        } finally {
            a.recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionId = event.action
        val newColor: Int
        when (actionId) {
            MotionEvent.ACTION_DOWN -> isClick = true
            MotionEvent.ACTION_UP -> {
                newColor = getColorAtXY(event.x, event.y)
                setSelectedColor(newColor)
                if (isClick) {
                    performClick()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                newColor = getColorAtXY(event.x, event.y)
                setSelectedColor(newColor)
            }
            MotionEvent.ACTION_CANCEL -> isClick = false
            MotionEvent.ACTION_OUTSIDE -> isClick = false
            else -> {}
        }
        return true
    }

    /**
     * Return color at x,y coordinate of view.
     */
    private fun getColorAtXY(
        x: Float,
        y: Float
    ): Int {
        if (colors.isEmpty()) {
            return 0
        }
        if (mOrientation == HORIZONTAL) {
            var left: Int
            var right = 0
            for (color in colors) {
                left = right
                right += cellSize
                if (left <= x && right >= x) {
                    return color
                }
            }
        } else {
            var top: Int
            var bottom = 0
            for (color in colors) {
                top = bottom
                bottom += cellSize
                if (y >= top && y <= bottom) {
                    return color
                }
            }
        }
        return color
    }

    override fun onSaveInstanceState(): Parcelable {
        // begin boilerplate code that allows parent classes to save state
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        // end
        ss.selectedColor = color
        ss.isColorSelected = isColorSelected
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        // begin boilerplate code so parent classes can restore state
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        // end
        color = state.selectedColor
        isColorSelected = state.isColorSelected
    }

    internal class SavedState : BaseSavedState {
        var selectedColor = 0
        var isColorSelected = false

        constructor(superState: Parcelable?) : super(superState)
        private constructor(`in`: Parcel) : super(`in`) {
            selectedColor = `in`.readInt()
            isColorSelected = `in`.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(selectedColor)
            out.writeInt(if (isColorSelected) 1 else 0)
        }

        companion object {
            // required field that makes Parcelables from a Parcel
            val CREATOR: Parcelable.Creator<SavedState?> =
                object : Parcelable.Creator<SavedState?> {
                    override fun createFromParcel(`in`: Parcel): SavedState {
                        return SavedState(`in`)
                    }

                    override fun newArray(size: Int): Array<SavedState?> {
                        return arrayOfNulls(size)
                    }
                }
        }

        override fun describeContents(): Int {
            return 0
        }

        object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun onSizeChanged(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        screenW = w
        screenH = h
        recalcCellSize()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    @Suppress("unused")
    fun setSelectedColor(color: Int) {

        // not from current palette
        if (!containsColor(colors, color)) {
            return
        }

        // do we need to re-draw view?
        if (!isColorSelected || this.color != color) {
            this.color = color
            isColorSelected = true
            invalidate()
            onColorChanged(color)
        }
    }

    /**
     * Set selected color as index from palete
     */
    @Suppress("unused")
    fun setSelectedColorPosition(position: Int) {
        setSelectedColor(colors[position])
    }

    /**
     * Set picker palette
     */
    @Suppress("unused")
    fun setColorArray(colors: IntArray) {
        this.colors = colors
        if (!containsColor(colors, color)) {
            color = colors[0]
        }
        recalcCellSize()
        invalidate()
    }

    private fun recalcCellSize(): Int {
        cellSize = if (mOrientation == HORIZONTAL) {
            (screenW / (colors.size * 1f)).roundToInt()
        } else {
            (screenH / (colors.size * 1f)).roundToInt()
        }
        return cellSize
    }

    /**
     * Return true if palette contains this color
     */
    private fun containsColor(
        colors: IntArray,
        c: Int
    ): Boolean {
        for (color in colors) {
            if (color == c) return true
        }
        return false
    }

    @Suppress("unused")
    fun setOnColorChangedListener(l: OnColorChangedListener?) {
        onColorChanged = l
    }

}
