package com.loitpcore.views.layout.flow

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import com.loitpcore.R
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    companion object {

        /**
         * Special value for the child view spacing.
         * SPACING_AUTO means that the actual spacing is calculated according to the size of the
         * container and the number of the child views, so that the child views are placed evenly in
         * the container.
         */
        const val SPACING_AUTO = -65536

        /**
         * Special value for the horizontal spacing of the child views in the last row
         * SPACING_ALIGN means that the horizontal spacing of the child views in the last row keeps
         * the same with the spacing used in the row above. If there is only one row, this value is
         * ignored and the spacing will be calculated according to childSpacing.
         */
        const val SPACING_ALIGN = -65537
        private const val SPACING_UNDEFINED = -65538
        private const val UNSPECIFIED_GRAVITY = -1
        private const val ROW_VERTICAL_GRAVITY_AUTO = -65536
        private const val DEFAULT_FLOW = true
        private const val DEFAULT_CHILD_SPACING = 0
        private const val DEFAULT_CHILD_SPACING_FOR_LAST_ROW = SPACING_UNDEFINED
        private const val DEFAULT_ROW_SPACING = 0f
        private const val DEFAULT_RTL = false
        private const val DEFAULT_MAX_ROWS = Int.MAX_VALUE
    }

    private var mFlow = DEFAULT_FLOW
    private var mChildSpacing = DEFAULT_CHILD_SPACING
    private var mMinChildSpacing = DEFAULT_CHILD_SPACING
    private var mChildSpacingForLastRow = DEFAULT_CHILD_SPACING_FOR_LAST_ROW
    private var mRowSpacing = DEFAULT_ROW_SPACING
    private var mAdjustedRowSpacing = DEFAULT_ROW_SPACING
    private var mRtl = DEFAULT_RTL
    private var mMaxRows = DEFAULT_MAX_ROWS
    private var mGravity = UNSPECIFIED_GRAVITY
    private var mRowVerticalGravity = ROW_VERTICAL_GRAVITY_AUTO
    private var mExactMeasuredHeight = 0
    private val mHorizontalSpacingForRow: MutableList<Float> = ArrayList()
    private val mHeightForRow: MutableList<Int> = ArrayList()
    private val mWidthForRow: MutableList<Int> = ArrayList()
    private val mChildNumForRow: MutableList<Int> = ArrayList()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        mHorizontalSpacingForRow.clear()
        mHeightForRow.clear()
        mWidthForRow.clear()
        mChildNumForRow.clear()
        var measuredHeight = 0
        var measuredWidth = 0
        val childCount = childCount
        var rowWidth = 0
        var maxChildHeightInRow = 0
        var childNumInRow = 0
        val rowSize = widthSize - paddingLeft - paddingRight
        var rowTotalChildWidth = 0
        val allowFlow = widthMode != MeasureSpec.UNSPECIFIED && mFlow
        val childSpacing =
            if (mChildSpacing == SPACING_AUTO && widthMode == MeasureSpec.UNSPECIFIED) 0 else mChildSpacing
        val tmpSpacing =
            if (childSpacing == SPACING_AUTO) mMinChildSpacing.toFloat() else childSpacing.toFloat()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }
            val childParams = child.layoutParams
            var horizontalMargin = 0
            var verticalMargin = 0
            if (childParams is MarginLayoutParams) {
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    measuredHeight
                )
                horizontalMargin = childParams.leftMargin + childParams.rightMargin
                verticalMargin = childParams.topMargin + childParams.bottomMargin
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
            }
            val childWidth = child.measuredWidth + horizontalMargin
            val childHeight = child.measuredHeight + verticalMargin
            if (allowFlow && rowWidth + childWidth > rowSize) {
                // Need flow to next row
                // Save parameters for current row
                mHorizontalSpacingForRow.add(
                    getSpacingForRow(
                        spacingAttribute = childSpacing,
                        rowSize = rowSize,
                        usedSize = rowTotalChildWidth,
                        childNum = childNumInRow
                    )
                )
                mChildNumForRow.add(childNumInRow)
                mHeightForRow.add(maxChildHeightInRow)
                mWidthForRow.add(rowWidth - tmpSpacing.toInt())
                if (mHorizontalSpacingForRow.size <= mMaxRows) {
                    measuredHeight += maxChildHeightInRow
                }
                measuredWidth = max(a = measuredWidth, b = rowWidth)

                // Place the child view to next row
                childNumInRow = 1
                rowWidth = childWidth + tmpSpacing.toInt()
                rowTotalChildWidth = childWidth
                maxChildHeightInRow = childHeight
            } else {
                childNumInRow++
                rowWidth += (childWidth + tmpSpacing).toInt()
                rowTotalChildWidth += childWidth
                maxChildHeightInRow = max(a = maxChildHeightInRow, b = childHeight)
            }
        }

        // Measure remaining child views in the last row
        if (mChildSpacingForLastRow == SPACING_ALIGN) {
            // For SPACING_ALIGN, use the same spacing from the row above if there is more than one
            // row.
            if (mHorizontalSpacingForRow.size >= 1) {
                mHorizontalSpacingForRow.add(mHorizontalSpacingForRow[mHorizontalSpacingForRow.size - 1])
            } else {
                mHorizontalSpacingForRow.add(
                    getSpacingForRow(
                        childSpacing,
                        rowSize,
                        rowTotalChildWidth,
                        childNumInRow
                    )
                )
            }
        } else if (mChildSpacingForLastRow != SPACING_UNDEFINED) {
            // For SPACING_AUTO and specific DP values, apply them to the spacing strategy.
            mHorizontalSpacingForRow.add(
                getSpacingForRow(
                    mChildSpacingForLastRow,
                    rowSize,
                    rowTotalChildWidth,
                    childNumInRow
                )
            )
        } else {
            // For SPACING_UNDEFINED, apply childSpacing to the spacing strategy for the last row.
            mHorizontalSpacingForRow.add(
                getSpacingForRow(
                    childSpacing,
                    rowSize,
                    rowTotalChildWidth,
                    childNumInRow
                )
            )
        }
        mChildNumForRow.add(childNumInRow)
        mHeightForRow.add(maxChildHeightInRow)
        mWidthForRow.add(rowWidth - tmpSpacing.toInt())
        if (mHorizontalSpacingForRow.size <= mMaxRows) {
            measuredHeight += maxChildHeightInRow
        }
        measuredWidth = max(measuredWidth, rowWidth)
        measuredWidth = when {
            childSpacing == SPACING_AUTO -> {
                widthSize
            }
            widthMode == MeasureSpec.UNSPECIFIED -> {
                measuredWidth + paddingLeft + paddingRight
            }
            else -> {
                min(measuredWidth + paddingLeft + paddingRight, widthSize)
            }
        }
        measuredHeight += paddingTop + paddingBottom
        val rowNum = min(mHorizontalSpacingForRow.size, mMaxRows)
        val rowSpacing: Float =
            if (mRowSpacing == SPACING_AUTO.toFloat() && heightMode == MeasureSpec.UNSPECIFIED) {
                0f
            } else {
                mRowSpacing
            }
        if (rowSpacing == SPACING_AUTO.toFloat()) {
            mAdjustedRowSpacing = if (rowNum > 1) {
                ((heightSize - measuredHeight) / (rowNum - 1)).toFloat()
            } else {
                0f
            }
            measuredHeight = heightSize
        } else {
            mAdjustedRowSpacing = rowSpacing
            if (rowNum > 1) {
                measuredHeight =
                    if (heightMode == MeasureSpec.UNSPECIFIED) (measuredHeight + mAdjustedRowSpacing * (rowNum - 1)).toInt()
                    else min(
                        (measuredHeight + mAdjustedRowSpacing * (rowNum - 1)).toInt(),
                        heightSize
                    )
            }
        }
        mExactMeasuredHeight = measuredHeight
        measuredWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else measuredWidth
        measuredHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else measuredHeight
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        var x = if (mRtl) {
            width - paddingRight
        } else {
            paddingLeft
        }
        var y = paddingTop
        val verticalGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK
        val horizontalGravity = mGravity and Gravity.HORIZONTAL_GRAVITY_MASK
        when (verticalGravity) {
            Gravity.CENTER_VERTICAL -> {
                val offset = (b - t - paddingTop - paddingBottom - mExactMeasuredHeight) / 2
                y += offset
            }
            Gravity.BOTTOM -> {
                val offset = b - t - paddingTop - paddingBottom - mExactMeasuredHeight
                y += offset
            }
            else -> {
            }
        }
        val horizontalPadding = paddingLeft + paddingRight
        val layoutWidth = r - l
        x += getHorizontalGravityOffsetForRow(
            horizontalGravity = horizontalGravity,
            parentWidth = layoutWidth,
            horizontalPadding = horizontalPadding,
            row = 0
        )
        val verticalRowGravity = mRowVerticalGravity and Gravity.VERTICAL_GRAVITY_MASK
        val rowCount = mChildNumForRow.size
        var childIdx = 0
        for (row in 0 until rowCount) {
            val childNum = mChildNumForRow[row]
            val rowHeight = mHeightForRow[row]
            val spacing = mHorizontalSpacingForRow[row]
            var i = 0
            while (i < childNum && childIdx < childCount) {
                val child = getChildAt(childIdx++)
                if (child.visibility == GONE) {
                    continue
                } else {
                    i++
                }
                val childParams = child.layoutParams
                var marginLeft = 0
                var marginTop = 0
                var marginBottom = 0
                var marginRight = 0
                if (childParams is MarginLayoutParams) {
                    marginLeft = childParams.leftMargin
                    marginRight = childParams.rightMargin
                    marginTop = childParams.topMargin
                    marginBottom = childParams.bottomMargin
                }
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                var tt = y + marginTop
                if (verticalRowGravity == Gravity.BOTTOM) {
                    tt = y + rowHeight - marginBottom - childHeight
                } else if (verticalRowGravity == Gravity.CENTER_VERTICAL) {
                    tt = y + marginTop + (rowHeight - marginTop - marginBottom - childHeight) / 2
                }
                val bb = tt + childHeight
                if (mRtl) {
                    val l1 = x - marginRight - childWidth
                    val r1 = x - marginRight
                    child.layout(l1, tt, r1, bb)
                    x -= (childWidth + spacing + marginLeft + marginRight).toInt()
                } else {
                    val l2 = x + marginLeft
                    val r2 = x + marginLeft + childWidth
                    child.layout(l2, tt, r2, bb)
                    x += (childWidth + spacing + marginLeft + marginRight).toInt()
                }
            }
            x = if (mRtl) width - paddingRight else paddingLeft
            x += getHorizontalGravityOffsetForRow(
                horizontalGravity = horizontalGravity,
                parentWidth = layoutWidth,
                horizontalPadding = horizontalPadding,
                row = row + 1
            )
            y += (rowHeight + mAdjustedRowSpacing).toInt()
        }
    }

    private fun getHorizontalGravityOffsetForRow(
        horizontalGravity: Int,
        parentWidth: Int,
        horizontalPadding: Int,
        row: Int
    ): Int {
        if (mChildSpacing == SPACING_AUTO || row >= mWidthForRow.size || row >= mChildNumForRow.size || mChildNumForRow[row] <= 0) {
            return 0
        }
        var offset = 0
        when (horizontalGravity) {
            Gravity.CENTER_HORIZONTAL ->
                offset =
                    (parentWidth - horizontalPadding - mWidthForRow[row]) / 2
            Gravity.END -> offset = parentWidth - horizontalPadding - mWidthForRow[row]
            else -> {
            }
        }
        return offset
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
    /**
     * Returns whether to allow child views flow to next row when there is no enough space.
     *
     * @return Whether to flow child views to next row when there is no enough space.
     */
    /**
     * Sets whether to allow child views flow to next row when there is no enough space.
     *
     * param flow true to allow flow. false to restrict all child views in one row.
     */
    var isFlow: Boolean
        get() = mFlow
        set(flow) {
            mFlow = flow
            requestLayout()
        }
    /**
     * Returns the horizontal spacing between child views.
     *
     * @return The spacing, either [FlowLayout.SPACING_AUTO], or a fixed size in pixels.
     */
    /**
     * Sets the horizontal spacing between child views.
     *
     * param childSpacing The spacing, either [FlowLayout.SPACING_AUTO], or a fixed size in
     * pixels.
     */
    var childSpacing: Int
        get() = mChildSpacing
        set(childSpacing) {
            mChildSpacing = childSpacing
            requestLayout()
        }
    /**
     * Returns the horizontal spacing between child views of the last row.
     *
     * @return The spacing, either [FlowLayout.SPACING_AUTO],
     * [FlowLayout.SPACING_ALIGN], or a fixed size in pixels
     */
    /**
     * Sets the horizontal spacing between child views of the last row.
     *
     * param childSpacingForLastRow The spacing, either [FlowLayout.SPACING_AUTO],
     * [FlowLayout.SPACING_ALIGN], or a fixed size in pixels
     */
    var childSpacingForLastRow: Int
        get() = mChildSpacingForLastRow
        set(childSpacingForLastRow) {
            mChildSpacingForLastRow = childSpacingForLastRow
            requestLayout()
        }
    /**
     * Returns the vertical spacing between rows.
     *
     * @return The spacing, either [FlowLayout.SPACING_AUTO], or a fixed size in pixels.
     */
    /**
     * Sets the vertical spacing between rows in pixels. Use SPACING_AUTO to evenly place all rows
     * in vertical.
     *
     * param rowSpacing The spacing, either [FlowLayout.SPACING_AUTO], or a fixed size in
     * pixels.
     */
    var rowSpacing: Float
        get() = mRowSpacing
        set(rowSpacing) {
            mRowSpacing = rowSpacing
            requestLayout()
        }
    /**
     * Returns the maximum number of rows of the FlowLayout.
     *
     * @return The maximum number of rows.
     */
    /**
     * Sets the height of the FlowLayout to be at most maxRows tall.
     *
     * param maxRows The maximum number of rows.
     */
    var maxRows: Int
        get() = mMaxRows
        set(maxRows) {
            mMaxRows = maxRows
            requestLayout()
        }

    fun setGravity(gravity: Int) {
        if (mGravity != gravity) {
            mGravity = gravity
            requestLayout()
        }
    }

    fun setRowVerticalGravity(rowVerticalGravity: Int) {
        if (mRowVerticalGravity != rowVerticalGravity) {
            mRowVerticalGravity = rowVerticalGravity
            requestLayout()
        }
    }

    var isRtl: Boolean
        get() = mRtl
        set(rtl) {
            mRtl = rtl
            requestLayout()
        }
    var minChildSpacing: Int
        get() = mMinChildSpacing
        set(minChildSpacing) {
            mMinChildSpacing = minChildSpacing
            requestLayout()
        }
    val rowsCount: Int
        get() = mChildNumForRow.size

    private fun getSpacingForRow(
        spacingAttribute: Int,
        rowSize: Int,
        usedSize: Int,
        childNum: Int
    ): Float {
        return if (spacingAttribute == SPACING_AUTO) {
            if (childNum > 1) {
                ((rowSize - usedSize) / (childNum - 1)).toFloat()
            } else {
                0f
            }
        } else {
            spacingAttribute.toFloat()
        }
    }

    private fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
        try {
            mFlow = typedArray.getBoolean(R.styleable.FlowLayout_flFlow, DEFAULT_FLOW)
            mChildSpacing = try {
                typedArray.getInt(R.styleable.FlowLayout_flChildSpacing, DEFAULT_CHILD_SPACING)
            } catch (e: NumberFormatException) {
                typedArray.getDimensionPixelSize(
                    R.styleable.FlowLayout_flChildSpacing,
                    dpToPx(DEFAULT_CHILD_SPACING.toFloat()).toInt()
                )
            }
            mMinChildSpacing = try {
                typedArray.getInt(R.styleable.FlowLayout_flMinChildSpacing, DEFAULT_CHILD_SPACING)
            } catch (e: NumberFormatException) {
                typedArray.getDimensionPixelSize(
                    R.styleable.FlowLayout_flMinChildSpacing,
                    dpToPx(DEFAULT_CHILD_SPACING.toFloat()).toInt()
                )
            }
            mChildSpacingForLastRow = try {
                typedArray.getInt(
                    R.styleable.FlowLayout_flChildSpacingForLastRow,
                    SPACING_UNDEFINED
                )
            } catch (e: NumberFormatException) {
                typedArray.getDimensionPixelSize(
                    R.styleable.FlowLayout_flChildSpacingForLastRow,
                    dpToPx(DEFAULT_CHILD_SPACING.toFloat()).toInt()
                )
            }
            mRowSpacing = try {
                typedArray.getInt(R.styleable.FlowLayout_flRowSpacing, 0).toFloat()
            } catch (e: NumberFormatException) {
                typedArray.getDimension(
                    R.styleable.FlowLayout_flRowSpacing,
                    dpToPx(DEFAULT_ROW_SPACING)
                )
            }
            mMaxRows = typedArray.getInt(R.styleable.FlowLayout_flMaxRows, DEFAULT_MAX_ROWS)
            mRtl = typedArray.getBoolean(R.styleable.FlowLayout_flRtl, DEFAULT_RTL)
            //TODO fix style
            mGravity =
                typedArray.getInt(R.styleable.FlowLayout_android_gravity, UNSPECIFIED_GRAVITY)
            mRowVerticalGravity = typedArray.getInt(
                R.styleable.FlowLayout_flRowVerticalGravity,
                ROW_VERTICAL_GRAVITY_AUTO
            )
        } finally {
            typedArray.recycle()
        }
    }
}
