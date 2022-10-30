package com.loitpcore.views.textView.selectable

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.style.BackgroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnTouchModeChangeListener
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import com.loitpcore.BuildConfig
import com.loitpcore.R
import com.loitpcore.core.utilities.LAppResource
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CustomTextView : AppCompatTextView {
    private var mDefaultSelectionColor = 0
    var cursorSelection: CustomInfo? = null
    private val mTempCoords = IntArray(2)
    private var mOnCursorStateChangedListener: OnCursorStateChangedListener? = null

    /**
     * DONT ACCESS DIRECTLY, use getSelectionController() instead
     */
    private var mSelectionController: SelectionCursorController? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        setDefaultSelectionColor(LAppResource.getColor(R.color.gray))
        cursorSelection = CustomInfo()
        mSelectionController = SelectionCursorController()
        val observer = viewTreeObserver
        observer?.addOnTouchModeChangeListener(mSelectionController)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // TextView will take care of the color span of the text when it's being scroll by
        // its parent ScrollView. But the cursors position is not handled. Calling snapToSelection
        // will move the cursors along with the selection.
        if (parent is ObservableScrollView) {
            (parent as ObservableScrollView).addOnScrollChangedListener(
                object : OnScrollChangedListener {
                    override fun onScrollChanged(
                        scrollView: ObservableScrollView?,
                        x: Int,
                        y: Int,
                        oldx: Int,
                        oldy: Int
                    ) {
                        mSelectionController?.snapToSelection()
                    }
                })
        }
    }

    @Suppress("unused")
    fun setDefaultSelectionColor(color: Int) {
        mDefaultSelectionColor = color
    }

    @Suppress("unused")
    fun setSelection(color: Int, start: Int, length: Int, duration: Int) {
        val end = if (start + length >= text.length) {
            text.length
        } else {
            start + length
        }
        if (start + length > text.length || end <= start) {
            return
        }
        cursorSelection = CustomInfo(
            text = text,
            span = BackgroundColorSpan(color),
            start = start,
            end = end
        )
        cursorSelection?.select()
        removeSelection(duration)
    }

    @Suppress("unused")
    fun setSelection(start: Int, length: Int, duration: Int) {
        setSelection(
            color = mDefaultSelectionColor,
            start = start,
            length = length,
            duration = duration
        )
    }

    fun setSelection(start: Int, length: Int) {
        setSelection(
            start = start,
            length = length,
            duration = -1
        )
    }

    /**
     * removes the current selection immediately
     */
    fun removeSelection() {
        cursorSelection?.remove()
    }

    @Suppress("unused")
    fun removeSelection(delay: Int) {
        removeSelection(
            selection = cursorSelection,
            delay = delay
        )
    }

    @Suppress("unused")
    fun removeSelection(selection: CustomInfo?, delay: Int) {
        if (delay >= 0) {
            val runnable = Runnable {
                selection?.remove()
            }
            postDelayed(runnable, delay.toLong())
        }
    }

    @Suppress("NAME_SHADOWING")
    fun showSelectionControls(start: Int, end: Int) {
        var start = start
        var end = end
        if (start < 0) {
            start = 0
        } else if (start > text.length) {
            start = text.length
        }
        if (end < 0) {
            end = 0
        } else if (end > text.length) {
            end = text.length
        }
        mSelectionController?.show(start = start, end = end)
    }

    fun setOnCursorStateChangedListener(onCursorStateChangedListener: OnCursorStateChangedListener?) {
        mOnCursorStateChangedListener = onCursorStateChangedListener
    }

    private val scrollYInternal: Int
        get() {
            var y = this.scrollY
            if (this.parent is ScrollView) {
                y += (this.parent as ScrollView).scrollY
                val coords = mTempCoords
                (this.parent as ScrollView).getLocationInWindow(coords)
                y -= coords[1]
            }
            return y
        }

    // a TextView inside a ScrollView is not scrolled, so getScrollX() returns 0.
    // We must use getScrollX() from the ScrollView instead
    private val scrollXInternal: Int
        get() {
            var x = this.scrollX

            // a TextView inside a ScrollView is not scrolled, so getScrollX() returns 0.
            // We must use getScrollX() from the ScrollView instead
            if (this.parent is ScrollView) {
                val scrollView = this.parent as ScrollView
                x += scrollView.scrollX
                val coords = mTempCoords
                scrollView.getLocationInWindow(coords)
                x -= coords[0]
                x -= scrollView.paddingLeft
            }
            return x
        }

    @Suppress("unused")
    fun getOffset(x: Int, y: Int): Int {
        val layout = layout
        var offset = -1
        if (layout != null) {
            val topVisibleLine = layout.getLineForVertical(y)
            offset = layout.getOffsetForHorizontal(topVisibleLine, x.toFloat())
        }
        return offset
    }

    @Suppress("unused")
    fun getPreciseOffset(x: Int, y: Int): Int {
        val layout = layout
        if (layout != null) {
            val topVisibleLine = layout.getLineForVertical(y)
            val offset = layout.getOffsetForHorizontal(topVisibleLine, x.toFloat())
            val offsetX = layout.getPrimaryHorizontal(offset).toInt()
            if (offsetX > x) {
                return layout.getOffsetToLeftOf(offset)
            }
        }
        return getOffset(x, y)
    }

    @Suppress("NAME_SHADOWING")
    private fun getHysteresisOffset(x: Int, y: Int, previousOffset: Int): Int {
        var x = x
        var y = y
        var previousOffset = previousOffset
        val layout = layout ?: return -1
        y += scrollYInternal
        x += scrollXInternal
        var line = getLayout().getLineForVertical(y)

        // //////////////////HACK BLOCK////////////////////////////////////////////////////
        if (isEndOfLineOffset(previousOffset)) {
            // we have to minus one from the offset so that the code below to find
            // the previous line can work correctly.
            val left = layout.getPrimaryHorizontal(previousOffset - 1).toInt()
            val right = layout.getLineRight(line).toInt()
            val threshold = (right - left) / 2 // half the width of the last character
            if (x > right - threshold) {
                previousOffset -= 1
            }
        }
        // /////////////////////////////////////////////////////////////////////////////////
        val previousLine = layout.getLineForOffset(previousOffset)
        val previousLineTop = layout.getLineTop(previousLine)
        val previousLineBottom = layout.getLineBottom(previousLine)
        val hysteresisThreshold = (previousLineBottom - previousLineTop) / 2
        if (line == previousLine + 1 && y - previousLineBottom < hysteresisThreshold ||
            (line == previousLine - 1) && previousLineTop - y < hysteresisThreshold
        ) {
            line = previousLine
        }
        var offset = layout.getOffsetForHorizontal(line, x.toFloat())
        // ///////////////////HACK BLOCK///////////////////////////////////////////////////
        if (offset < text.length - 1) {
            if (isEndOfLineOffset(offset + 1)) {
                val left = layout.getPrimaryHorizontal(offset).toInt()
                val right = layout.getLineRight(line).toInt()
                val threshold = (right - left) / 2 // half the width of the last character
                if (x > right - threshold) {
                    offset += 1
                }
            }
        }

        // ////////////////////////////////////////////////////////////////////////////////
        val str = text.toString()
        if (offset > 1 && offset < str.length) {
            for (i in offset downTo 0) {
                if (str[i] == ' ' || str[i] == '\n') {
                    offset = i
                    break
                } else {
                    offset--
                }
            }
        }
        if (offset < 0) {
            offset = 0
        }
        val test = str.split(" ".toRegex()).toTypedArray()
        if (test.size == 1) {
            // offset =
            offset = if (offset == 0) {
                0
            } else {
                str.length
            }
        }
        return offset
    }

    private fun isEndOfLineOffset(offset: Int): Boolean {
        return if (offset > 0) {
            layout.getLineForOffset(offset) == layout.getLineForOffset(offset - 1) + 1
        } else false
    }

    @Suppress("NAME_SHADOWING")
    private fun getOffsetForHorizontal(line: Int, x: Int): Int {
        var x = x
        x -= totalPaddingLeft
        // Clamp the position to inside of the view.
        x = max(0, x)
        x = min(width - totalPaddingRight - 1, x)
        x += scrollXInternal
        return layout.getOffsetForHorizontal(line, x.toFloat())
    }

    private fun getXY(offset: Int, scrollX: Int, scrollY: Int, coords: IntArray) {
        if (BuildConfig.DEBUG && coords.size < 2) {
            error("Assertion failed")
        }
        coords[1] = -1
        coords[0] = coords[1]
        val layout = layout
        if (layout != null) {
            val line = layout.getLineForOffset(offset)
            val base = layout.getLineBottom(line)
            coords[0] = layout.getPrimaryHorizontal(offset).toInt() - scrollX // x
            coords[1] = base - scrollY // y
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun getAdjusteStartXY(offset: Int, scrollX: Int, scrollY: Int, coords: IntArray) {
        var offset = offset
        if (offset < text.length) {
            val layout = layout
            if (layout != null) {
                if (isEndOfLineOffset(offset + 1)) {
                    val a = layout.getPrimaryHorizontal(offset)
                    val b = layout.getLineRight(layout.getLineForOffset(offset))
                    if (a == b) {
                        // this means the we encounter a new line character, i think.
                        offset += 1
                    }
                }
            }
        }
        getXY(offset = offset, scrollX = scrollX, scrollY = scrollY, coords = coords)
    }

    private fun getAdjustedEndXY(offset: Int, scrollX: Int, scrollY: Int, coords: IntArray) {
        if (offset > 0) {
            val layout = layout
            if (layout != null) {
                if (isEndOfLineOffset(offset)) {
                    val prevLine = layout.getLineForOffset(offset - 1)
                    val right = layout.getLineRight(prevLine)
                    val y = layout.getLineBottom(prevLine)
                    coords[0] = right.toInt() - scrollX
                    coords[1] = y - scrollY
                    return
                }
            }
        }
        getXY(offset = offset, scrollX = scrollX, scrollY = scrollY, coords = coords)
    }

    fun hideCursor() {
        mSelectionController?.hide()
    }

    private inner class SelectionCursorController : OnTouchModeChangeListener {
        private val mStartHandle: CursorHandle = CursorHandle(this)
        private val mEndHandle: CursorHandle = CursorHandle(this)
        var isShowing = false
            private set

        fun snapToSelection() {
            if (isShowing) {
                cursorSelection?.start?.let { a ->
                    cursorSelection?.end?.let { b ->
                        val start = min(a, b)
                        val end = max(a, b)

                        // find the corresponding handle for the start/end calculated above
                        val startHandle = if (start == a) mStartHandle else mEndHandle
                        val endHandle = if (end == b) mEndHandle else mStartHandle
                        val coords = mTempCoords
                        val scrollY = scrollYInternal
                        val scrollX = scrollXInternal
                        getAdjusteStartXY(
                            offset = start,
                            scrollX = scrollX,
                            scrollY = scrollY,
                            coords = coords
                        )
                        startHandle.pointTo(x = coords[0], y = coords[1])
                        getAdjustedEndXY(
                            offset = end,
                            scrollX = scrollX,
                            scrollY = scrollY,
                            coords = coords
                        )
                        endHandle.pointTo(x = coords[0], y = coords[1])
                        mOnCursorStateChangedListener?.onDragEnds(coords[0], coords[1])
                    }
                }
            }
        }

        fun show(start: Int, end: Int) {
            val a = min(start, end)
            val b = max(start, end)
            val coords = mTempCoords
            val scrollY = this@CustomTextView.scrollY
            val scrollX = this@CustomTextView.scrollX
            getAdjusteStartXY(offset = a, scrollX = scrollX, scrollY = scrollY, coords = coords)
            mStartHandle.show(x = coords[0], y = coords[1])
            getAdjustedEndXY(offset = b, scrollX = scrollX, scrollY = scrollY, coords = coords)
            mEndHandle.show(coords[0], coords[1])
            mOnCursorStateChangedListener?.onDragEnds(coords[0], coords[1])
            isShowing = true
            select(start = a, end = b)
        }

        fun hide() {
            if (isShowing) {
                this@CustomTextView.removeSelection()
                mStartHandle.hide()
                mEndHandle.hide()
                isShowing = false
            }
        }

        fun updatePosition(cursorHandle: CursorHandle, x: Int, y: Int, oldx: Int, oldy: Int) {
            if (!isShowing) {
                return
            }
            val previousOffset =
                if (cursorHandle === mStartHandle) cursorSelection?.start else cursorSelection?.end
            val offset = getHysteresisOffset(x, y, previousOffset ?: 0)
            if (offset != previousOffset) {
                if (cursorHandle === mStartHandle) {
                    cursorSelection?.start = offset
                } else {
                    cursorSelection?.end = offset
                }
                cursorSelection?.select()
            }

            cursorHandle.pointTo(x = x, y = y)
            mOnCursorStateChangedListener?.onPositionChanged(
                v = this@CustomTextView,
                x = x,
                y = y,
                oldx = oldx,
                oldy = oldy
            )
        }

        private fun select(start: Int, end: Int) {
            this@CustomTextView.setSelection(min(start, end), abs(end - start))
        }

        override fun onTouchModeChanged(isInTouchMode: Boolean) {
            if (!isInTouchMode) {
                hide()
            }
        }
    }

    private inner class CursorHandle(
        private val mController: SelectionCursorController
    ) : View(this@CustomTextView.context) {
        private val mContainer: PopupWindow = PopupWindow(this)
        private val mDrawable: Drawable? = LAppResource.getDrawable(R.drawable.l_cursor)
        private var mIsDragging = false
        private val mHeight: Int
        private val mWidth: Int
        private val mHotspotX: Int
        private val mHotspotY: Int
        private var mAdjustX = 0
        private var mAdjustY = 0
        private var mOldX = 0
        private var mOldY = 0

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            setMeasuredDimension(mWidth, mHeight)
        }

        override fun onDraw(canvas: Canvas) {
            mDrawable?.setBounds(0, 0, mWidth, mHeight)
            mDrawable?.draw(canvas)
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            val rawX = event.rawX.toInt()
            val rawY = event.rawY.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mAdjustX = mHotspotX - event.x.toInt()
                    mAdjustY = mHotspotY - event.y.toInt()
                    mOldX = mAdjustX + rawX
                    mOldY = mAdjustY + rawY
                    mIsDragging = true
                    mOnCursorStateChangedListener?.onDragStarts(this@CustomTextView)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    mIsDragging = false
                    mController.snapToSelection()
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = mAdjustX + rawX
                    val y = mAdjustY + rawY
                    mController.updatePosition(
                        cursorHandle = this,
                        x = x,
                        y = y,
                        oldx = mOldX,
                        oldy = mOldY
                    )
                    mOldX = x
                    mOldY = y
                }
            }
            return true
        }

        val isShowing: Boolean
            get() = mContainer.isShowing

        fun show(x: Int, y: Int) {
            val coords = mTempCoords
            this@CustomTextView.getLocationInWindow(coords)
            coords[0] += x - mHotspotX
            coords[1] += y - mHotspotY
            mContainer.showAtLocation(this@CustomTextView, Gravity.NO_GRAVITY, coords[0], coords[1])
        }

        fun pointTo(x: Int, y: Int) {
            if (isShowing) {
                mContainer.update(x - mHotspotX, y - mHotspotY, -1, -1)
            }
        }

        fun hide() {
            mIsDragging = false
            mContainer.dismiss()
        }

        init {
            // mContainer.setSplitTouchEnabled(true);
            mContainer.isClippingEnabled = false

            /* My Note
             getIntrinsicWidth() returns the width of the drawable after it has been
             scaled to the current device's density
             e.g. if the drawable is a 15 x 20 image and we load the image on a Nexus 4 (which
             has a density of 2.0), getIntrinsicWidth() shall return 15 * 2 = 30
			 */mHeight = mDrawable!!.intrinsicHeight
            mWidth = mDrawable.intrinsicWidth

            // the PopupWindow has an initial dimension of (0, 0)
            // must set the width/height of the popupwindow in order for it to be drawn
            mContainer.width = mWidth
            mContainer.height = mHeight
            mHotspotX = mWidth / 2
            mHotspotY = 0
            invalidate()
        }
    }

    interface OnCursorStateChangedListener {
        fun onDragStarts(v: View?)
        fun onPositionChanged(v: View?, x: Int, y: Int, oldx: Int, oldy: Int)
        fun onDragEnds(endHandleX: Int, endHandleY: Int)
    }
}
