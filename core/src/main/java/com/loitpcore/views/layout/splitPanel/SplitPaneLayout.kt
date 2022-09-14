package com.loitpcore.views.layout.splitPanel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SplitPaneLayout : ViewGroup {

    companion object {
        const val ORIENTATION_HORIZONTAL = 0
        const val ORIENTATION_VERTICAL = 1
        private fun clamp(value: Float, min: Float, max: Float): Float {
            if (value < min) {
                return min
            } else if (value > max) {
                return max
            }
            return value
        }

        private fun clamp(value: Int, min: Int, max: Int): Int {
            if (value < min) {
                return min
            } else if (value > max) {
                return max
            }
            return value
        }

        private fun between(value: Int, min: Int, max: Int): Boolean {
            return value in min..max
        }
    }

    interface OnSplitterPositionChangedListener {
        fun onSplitterPositionChanged(splitPaneLayout: SplitPaneLayout, fromUser: Boolean)
    }

    private var mOrientation = 0
    private var mSplitterSize = 8
    /**
     * Gets whether the splitter is movable by the user.
     *
     * @return whether the splitter is movable
     */
    /**
     * Sets whether the splitter is movable by the user.
     *
     * splitterMovable whether the splitter is movable
     */
    var isSplitterMovable = true
    private var mSplitterPosition = Int.MIN_VALUE
    private var mSplitterPositionPercent = 0.5f
    private var mSplitterTouchSlop = 0
    private var minSplitterPosition = 0
    private var mSplitterDrawable: Drawable? = null
    private var mSplitterDraggingDrawable: Drawable? = null
    private val mSplitterBounds = Rect()
    private val mSplitterTouchBounds = Rect()
    private val mSplitterDraggingBounds = Rect()
    /**
     * Gets the OnSplitterPositionChangedListener to receive callbacks when the splitter position is changed
     *
     * @return the OnSplitterPositionChangedListener to receive callbacks when the splitter position is changed
     */
    /**
     * Sets the OnSplitterPositionChangedListener to receive callbacks when the splitter position is changed
     *
     * l the OnSplitterPositionChangedListener to receive callbacks when the splitter position is changed
     */
    var onSplitterPositionChangedListener: OnSplitterPositionChangedListener? = null
    private var lastTouchX = 0
    private var lastTouchY = 0
    private var isDragging = false
    private var isMovingSplitter = false
    private var isMeasured = false

    constructor(context: Context?) : super(context) {
        mSplitterPositionPercent = 0.5f
        mSplitterDrawable = PaintDrawable(-0x77000001)
        mSplitterDraggingDrawable = PaintDrawable(-0x77000001)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        extractAttributes(context, attrs)
        descendantFocusability = FOCUS_AFTER_DESCENDANTS
        isFocusable = true
        isFocusableInTouchMode = false
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        extractAttributes(context, attrs)
        descendantFocusability = FOCUS_AFTER_DESCENDANTS
        isFocusable = true
        isFocusableInTouchMode = false
    }

    private fun extractAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SplitPaneLayout)
            mOrientation = typedArray.getInt(R.styleable.SplitPaneLayout_splitterOrientation, 0)
            mSplitterSize = typedArray.getDimensionPixelSize(
                R.styleable.SplitPaneLayout_splitterSize,
                context.resources.getDimensionPixelSize(R.dimen.spl_default_splitter_size)
            )
            isSplitterMovable =
                typedArray.getBoolean(R.styleable.SplitPaneLayout_splitterMovable, true)
            var value = typedArray.peekValue(R.styleable.SplitPaneLayout_splitterPosition)
            if (value != null) {
                if (value.type == TypedValue.TYPE_DIMENSION) {
                    mSplitterPosition = typedArray.getDimensionPixelSize(
                        R.styleable.SplitPaneLayout_splitterPosition,
                        Int.MIN_VALUE
                    )
                } else if (value.type == TypedValue.TYPE_FRACTION) {
                    mSplitterPositionPercent = typedArray.getFraction(
                        R.styleable.SplitPaneLayout_splitterPosition,
                        100,
                        100,
                        50f
                    ) * 0.01f
                }
            } else {
                mSplitterPosition = Int.MIN_VALUE
                mSplitterPositionPercent = 0.5f
            }
            value = typedArray.peekValue(R.styleable.SplitPaneLayout_splitterBackground)
            if (value != null) {
                if (value.type == TypedValue.TYPE_REFERENCE ||
                    value.type == TypedValue.TYPE_STRING
                ) {
                    mSplitterDrawable =
                        typedArray.getDrawable(R.styleable.SplitPaneLayout_splitterBackground)
                } else if (value.type == TypedValue.TYPE_INT_COLOR_ARGB8 || value.type == TypedValue.TYPE_INT_COLOR_ARGB4 || value.type == TypedValue.TYPE_INT_COLOR_RGB8 || value.type == TypedValue.TYPE_INT_COLOR_RGB4) {
                    mSplitterDrawable = PaintDrawable(
                        typedArray.getColor(
                            R.styleable.SplitPaneLayout_splitterBackground,
                            -0x1000000
                        )
                    )
                }
            }
            value = typedArray.peekValue(R.styleable.SplitPaneLayout_splitterDraggingBackground)
            if (value != null) {
                if (value.type == TypedValue.TYPE_REFERENCE ||
                    value.type == TypedValue.TYPE_STRING
                ) {
                    mSplitterDraggingDrawable =
                        typedArray.getDrawable(R.styleable.SplitPaneLayout_splitterDraggingBackground)
                } else if (value.type == TypedValue.TYPE_INT_COLOR_ARGB8 || value.type == TypedValue.TYPE_INT_COLOR_ARGB4 || value.type == TypedValue.TYPE_INT_COLOR_RGB8 || value.type == TypedValue.TYPE_INT_COLOR_RGB4) {
                    mSplitterDraggingDrawable = PaintDrawable(
                        typedArray.getColor(
                            R.styleable.SplitPaneLayout_splitterDraggingBackground,
                            -0x77000001
                        )
                    )
                }
            } else {
                mSplitterDraggingDrawable = PaintDrawable(-0x77000001)
            }
            mSplitterTouchSlop = typedArray.getDimensionPixelSize(
                R.styleable.SplitPaneLayout_splitterTouchSlop,
                ViewConfiguration.get(context).scaledTouchSlop
            )
            minSplitterPosition = typedArray.getDimensionPixelSize(
                R.styleable.SplitPaneLayout_splitterPanelSizeMin,
                0
            )
            typedArray.recycle()
        }
    }

    private fun computeSplitterPosition() {
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        if (measuredWidth > 0 && measuredHeight > 0) {
            when (mOrientation) {
                ORIENTATION_HORIZONTAL -> {
                    if (mSplitterPosition == Int.MIN_VALUE && mSplitterPositionPercent < 0) {
                        mSplitterPosition = measuredWidth / 2
                    } else if (mSplitterPosition == Int.MIN_VALUE && mSplitterPositionPercent >= 0) {
                        mSplitterPosition = (measuredWidth * mSplitterPositionPercent).toInt()
                        if (!between(mSplitterPosition, minSplitterPosition, maxSplitterPosition)) {
                            mSplitterPosition =
                                clamp(mSplitterPosition, minSplitterPosition, maxSplitterPosition)
                            mSplitterPositionPercent =
                                mSplitterPosition.toFloat() / measuredWidth.toFloat()
                        }
                    } else if (mSplitterPosition != Int.MIN_VALUE && mSplitterPositionPercent < 0) {
                        if (!between(mSplitterPosition, minSplitterPosition, maxSplitterPosition)) {
                            mSplitterPosition =
                                clamp(mSplitterPosition, minSplitterPosition, maxSplitterPosition)
                        }
                        mSplitterPositionPercent =
                            mSplitterPosition.toFloat() / measuredWidth.toFloat()
                    }
                    mSplitterBounds[mSplitterPosition - mSplitterSize / 2, 0, mSplitterPosition + mSplitterSize / 2] =
                        measuredHeight
                    mSplitterTouchBounds[mSplitterBounds.left - mSplitterTouchSlop, mSplitterBounds.top, mSplitterBounds.right + mSplitterTouchSlop] =
                        mSplitterBounds.bottom
                }
                ORIENTATION_VERTICAL -> {
                    if (mSplitterPosition == Int.MIN_VALUE && mSplitterPositionPercent < 0) {
                        mSplitterPosition = measuredHeight / 2
                    } else if (mSplitterPosition == Int.MIN_VALUE && mSplitterPositionPercent >= 0) {
                        mSplitterPosition = (measuredHeight * mSplitterPositionPercent).toInt()
                        if (!between(mSplitterPosition, minSplitterPosition, maxSplitterPosition)) {
                            mSplitterPosition =
                                clamp(mSplitterPosition, minSplitterPosition, maxSplitterPosition)
                            mSplitterPositionPercent =
                                mSplitterPosition.toFloat() / measuredHeight.toFloat()
                        }
                    } else if (mSplitterPosition != Int.MIN_VALUE && mSplitterPositionPercent < 0) {
                        if (!between(mSplitterPosition, minSplitterPosition, maxSplitterPosition)) {
                            mSplitterPosition =
                                clamp(mSplitterPosition, minSplitterPosition, maxSplitterPosition)
                        }
                        mSplitterPositionPercent =
                            mSplitterPosition.toFloat() / measuredHeight.toFloat()
                    }
                    mSplitterBounds[0, mSplitterPosition - mSplitterSize / 2, measuredWidth] =
                        mSplitterPosition + mSplitterSize / 2
                    mSplitterTouchBounds[mSplitterBounds.left, mSplitterBounds.top - mSplitterTouchSlop / 2, mSplitterBounds.right] =
                        mSplitterBounds.bottom + mSplitterTouchSlop / 2
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        check()
        if (measuredWidth > 0 && measuredHeight > 0) {
            computeSplitterPosition()
            when (mOrientation) {
                ORIENTATION_HORIZONTAL -> {
                    getChildAt(0).measure(
                        MeasureSpec.makeMeasureSpec(
                            mSplitterPosition - mSplitterSize / 2,
                            MeasureSpec.EXACTLY
                        ),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
                    )
                    getChildAt(1).measure(
                        MeasureSpec.makeMeasureSpec(
                            measuredWidth - mSplitterSize / 2 - mSplitterPosition,
                            MeasureSpec.EXACTLY
                        ),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
                    )
                }
                ORIENTATION_VERTICAL -> {
                    getChildAt(0).measure(
                        MeasureSpec.makeMeasureSpec(
                            measuredWidth,
                            MeasureSpec.EXACTLY
                        ),
                        MeasureSpec.makeMeasureSpec(
                            mSplitterPosition - mSplitterSize / 2,
                            MeasureSpec.EXACTLY
                        )
                    )
                    getChildAt(1).measure(
                        MeasureSpec.makeMeasureSpec(
                            measuredWidth,
                            MeasureSpec.EXACTLY
                        ),
                        MeasureSpec.makeMeasureSpec(
                            measuredHeight - mSplitterSize / 2 - mSplitterPosition,
                            MeasureSpec.EXACTLY
                        )
                    )
                }
            }
            isMeasured = true
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val w = r - l
        val h = b - t
        when (mOrientation) {
            ORIENTATION_HORIZONTAL -> {
                getChildAt(0).layout(0, 0, mSplitterPosition - mSplitterSize / 2, h)
                getChildAt(1).layout(mSplitterPosition + mSplitterSize / 2, 0, r, h)
            }
            ORIENTATION_VERTICAL -> {
                getChildAt(0).layout(0, 0, w, mSplitterPosition - mSplitterSize / 2)
                getChildAt(1).layout(0, mSplitterPosition + mSplitterSize / 2, w, h)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var remeasure = false
        var offset = mSplitterSize
        if (event.isShiftPressed) {
            offset *= 5
        }
        when (mOrientation) {
            ORIENTATION_HORIZONTAL -> if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                mSplitterPosition =
                    clamp(mSplitterPosition - offset, minSplitterPosition, maxSplitterPosition)
                mSplitterPositionPercent = -1f
                remeasure = true
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                mSplitterPosition =
                    clamp(mSplitterPosition + offset, minSplitterPosition, maxSplitterPosition)
                mSplitterPositionPercent = -1f
                remeasure = true
            }
            ORIENTATION_VERTICAL -> if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                mSplitterPosition =
                    clamp(mSplitterPosition - offset, minSplitterPosition, maxSplitterPosition)
                mSplitterPositionPercent = -1f
                remeasure = true
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                mSplitterPosition =
                    clamp(mSplitterPosition + offset, minSplitterPosition, maxSplitterPosition)
                mSplitterPositionPercent = -1f
                remeasure = true
            }
        }
        if (remeasure) {
            remeasure()
            notifySplitterPositionChanged(true)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isSplitterMovable) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> handleTouchDown(x, y)
                MotionEvent.ACTION_MOVE -> handleTouchMove(x, y)
                MotionEvent.ACTION_UP -> handleTouchUp(x, y)
            }
            return true
        }
        return false
    }

    private fun handleTouchDown(x: Int, y: Int) {
        if (mSplitterTouchBounds.contains(x, y)) {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            isDragging = true
            mSplitterDraggingBounds.set(mSplitterBounds)
            invalidate(mSplitterDraggingBounds)
            lastTouchX = x
            lastTouchY = y
        }
    }

    private fun handleTouchMove(x: Int, y: Int) {
        if (isDragging) {
            if (!isMovingSplitter) {
                // Verify we've moved far enough to leave the touch bounds before moving the splitter
                isMovingSplitter = if (mSplitterTouchBounds.contains(x, y)) {
                    return
                } else {
                    true
                }
            }
            var take = true
            when (mOrientation) {
                ORIENTATION_HORIZONTAL -> {
                    mSplitterDraggingBounds.offset(x - lastTouchX, 0)
                    if (mSplitterDraggingBounds.centerX() < minSplitterPosition) {
                        take = false
                        mSplitterDraggingBounds.offset(
                            minSplitterPosition - mSplitterDraggingBounds.centerX(),
                            0
                        )
                    }
                    if (mSplitterDraggingBounds.centerX() > maxSplitterPosition) {
                        take = false
                        mSplitterDraggingBounds.offset(
                            maxSplitterPosition - mSplitterDraggingBounds.centerX(),
                            0
                        )
                    }
                }
                ORIENTATION_VERTICAL -> {
                    mSplitterDraggingBounds.offset(0, y - lastTouchY)
                    if (mSplitterDraggingBounds.centerY() < minSplitterPosition) {
                        take = false
                        mSplitterDraggingBounds.offset(
                            0,
                            minSplitterPosition - mSplitterDraggingBounds.centerY()
                        )
                    }
                    if (mSplitterDraggingBounds.centerY() > maxSplitterPosition) {
                        take = false
                        mSplitterDraggingBounds.offset(
                            0,
                            maxSplitterPosition - mSplitterDraggingBounds.centerY()
                        )
                    }
                }
            }
            if (take) {
                lastTouchX = x
                lastTouchY = y
            }
            invalidate()
        }
    }

    private fun handleTouchUp(x: Int, y: Int) {
        if (isDragging) {
            isDragging = false
            isMovingSplitter = false
            when (mOrientation) {
                ORIENTATION_HORIZONTAL -> {
                    mSplitterPosition = clamp(x, minSplitterPosition, maxSplitterPosition)
                    mSplitterPositionPercent = -1f
                }
                ORIENTATION_VERTICAL -> {
                    mSplitterPosition = clamp(y, minSplitterPosition, maxSplitterPosition)
                    mSplitterPositionPercent = -1f
                }
            }
            remeasure()
            notifySplitterPositionChanged(true)
        }
    }

    private val maxSplitterPosition: Int
        get() {
            when (mOrientation) {
                ORIENTATION_HORIZONTAL -> return measuredWidth - minSplitterPosition
                ORIENTATION_VERTICAL -> return measuredHeight - minSplitterPosition
            }
            return 0
        }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.mSplitterPositionPercent = mSplitterPositionPercent
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss = state
        super.onRestoreInstanceState(ss.superState)
        splitterPositionPercent = ss.mSplitterPositionPercent
    }

    /**
     * Convenience for calling own measure method.
     */
    private fun remeasure() {
        // check: Performance: Guard against calling too often, can it be done without requestLayout?
        forceLayout()
        measure(
            MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
        )
        requestLayout()
    }

    /**
     * Checks that we have exactly two children.
     */
    private fun check() {
        if (childCount != 2) {
            throw RuntimeException("SplitPaneLayout must have exactly two child views.")
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        mSplitterDrawable?.let {
            it.state = drawableState
            it.bounds = mSplitterBounds
            it.draw(canvas)
        }
        if (isDragging) {
            mSplitterDraggingDrawable?.let {
                it.state = drawableState
                it.bounds = mSplitterDraggingBounds
                it.draw(canvas)
            }
        }
    }
    /**
     * Gets the current drawable used for the splitter.
     *
     * @return the drawable used for the splitter
     */
    /**
     * Sets the drawable used for the splitter.
     *
     * splitterDrawable the desired orientation of the layout
     */
    var splitterDrawable: Drawable?
        get() = mSplitterDrawable
        set(splitterDrawable) {
            mSplitterDrawable = splitterDrawable
            if (childCount == 2) {
                remeasure()
            }
        }
    /**
     * Gets the current drawable used for the splitter dragging overlay.
     *
     * @return the drawable used for the splitter
     */
    /**
     * Sets the drawable used for the splitter dragging overlay.
     *
     * param splitterDraggingDrawable the drawable to use while dragging the splitter
     */
    var splitterDraggingDrawable: Drawable?
        get() = mSplitterDraggingDrawable
        set(splitterDraggingDrawable) {
            mSplitterDraggingDrawable = splitterDraggingDrawable
            if (isDragging) {
                invalidate()
            }
        }
    /**
     * Gets the current orientation of the layout.
     *
     * @return the orientation of the layout
     */
    /**
     * Sets the orientation of the layout.
     *
     * param orientation the desired orientation of the layout
     */
    var orientation: Int
        get() = mOrientation
        set(orientation) {
            if (mOrientation != orientation) {
                mOrientation = orientation
                if (childCount == 2) {
                    remeasure()
                }
            }
        }
    /**
     * Gets the current size of the splitter in pixels.
     *
     * @return the size of the splitter
     */
    /**
     * Sets the current size of the splitter in pixels.
     *
     * param splitterSize the desired size of the splitter
     */
    var splitterSize: Int
        get() = mSplitterSize
        set(splitterSize) {
            mSplitterSize = splitterSize
            if (childCount == 2) {
                remeasure()
            }
        }
    /**
     * Gets the current position of the splitter in pixels.
     *
     * @return the position of the splitter
     */
    /**
     * Sets the current position of the splitter in pixels.
     *
     * param position the desired position of the splitter
     */
    var splitterPosition: Int
        get() = mSplitterPosition
        set(position) {
            mSplitterPosition = clamp(position, 0, Int.MAX_VALUE)
            mSplitterPositionPercent = -1f
            remeasure()
            notifySplitterPositionChanged(false)
        }
    /**
     * Gets the current position of the splitter as a percent.
     *
     * @return the position of the splitter
     */
    /**
     * Sets the current position of the splitter as a percentage of the layout.
     *
     * param position the desired position of the splitter
     */
    var splitterPositionPercent: Float
        get() = mSplitterPositionPercent
        set(position) {
            mSplitterPosition = Int.MIN_VALUE
            mSplitterPositionPercent = clamp(position, 0f, 1f)
            remeasure()
            notifySplitterPositionChanged(false)
        }
    /**
     * Gets the current "touch slop" which is used to extends the grab size of the splitter
     * and requires the splitter to be dragged at least this far to be considered a move.
     *
     * @return the current "touch slop" of the splitter
     */
    /**
     * Sets the current "touch slop" which is used to extends the grab size of the splitter
     * and requires the splitter to be dragged at least this far to be considered a move.
     *
     * param splitterTouchSlop the desired "touch slop" of the splitter
     */
    var splitterTouchSlop: Int
        get() = mSplitterTouchSlop
        set(splitterTouchSlop) {
            mSplitterTouchSlop = splitterTouchSlop
            computeSplitterPosition()
        }
    /**
     * Gets the minimum size of panes, in pixels.
     *
     * @return the minimum size of panes, in pixels.
     */
    /**
     * Sets the minimum size of panes, in pixels.
     *
     * param paneSizeMin the minimum size of panes, in pixels
     */
    var paneSizeMin: Int
        get() = minSplitterPosition
        set(paneSizeMin) {
            minSplitterPosition = paneSizeMin
            if (isMeasured) {
                val newSplitterPosition =
                    clamp(mSplitterPosition, minSplitterPosition, maxSplitterPosition)
                if (newSplitterPosition != mSplitterPosition) {
                    splitterPosition = newSplitterPosition
                }
            }
        }

    private fun notifySplitterPositionChanged(fromUser: Boolean) {
        onSplitterPositionChangedListener?.onSplitterPositionChanged(this, fromUser)
    }

    /**
     * Holds important values when we need to save instance state.
     */
    class SavedState : BaseSavedState {
        var mSplitterPositionPercent = 0f

        internal constructor(superState: Parcelable?) : super(superState)

        private constructor(parcel: Parcel) : super(parcel) {
            mSplitterPositionPercent = parcel.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(mSplitterPositionPercent)
        }

//        companion object {
//            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState?> {
//                override fun createFromParcel(`in`: Parcel): SavedState? {
//                    return SavedState(`in`)
//                }
//
//                override fun newArray(size: Int): Array<SavedState?> {
//                    return arrayOfNulls(size)
//                }
//            }
//        }
    }
}
