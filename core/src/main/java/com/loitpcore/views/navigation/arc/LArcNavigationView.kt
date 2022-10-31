package com.loitpcore.views.navigation.arc

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.internal.ScrimInsetsFrameLayout
import com.google.android.material.navigation.NavigationView
import com.loitpcore.views.navigation.arc.LArcViewSettings.Companion.dpToPx
import kotlin.math.roundToInt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LArcNavigationView : NavigationView {

    companion object {
        private var THRESHOLD = 0
    }

    private var settings: LArcViewSettings? = null
    private var mHeight = 0
    private var mWidth = 0
    private var clipPath: Path? = null
    private var arcPath: Path? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        settings = LArcViewSettings(context, attrs)
        settings?.elevation = ViewCompat.getElevation(this)
        setBackgroundColor(Color.TRANSPARENT)
        setInsetsColor(Color.TRANSPARENT)
        THRESHOLD = dpToPx(
            context = getContext(),
            dp = 15
        ).roundToInt() //some threshold for child views while remeasuring them
    }

    @Suppress("unused")
    private fun setInsetsColor(color: Int) {
        try {
            val insetForegroundField =
                ScrimInsetsFrameLayout::class.java.getDeclaredField("mInsetForeground")
            insetForegroundField.isAccessible = true
            val colorDrawable = ColorDrawable(color)
            insetForegroundField[this] = colorDrawable
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun createClipPath(): Path {
        val path = Path()
        arcPath = Path()

        settings?.let { s ->
            val arcWidth = s.arcWidth
            val layoutParams = layoutParams as DrawerLayout.LayoutParams
            if (s.isCropInside) {
                when (layoutParams.gravity) {
                    Gravity.START, Gravity.LEFT -> {
                        arcPath?.let { p ->
                            p.moveTo(mWidth.toFloat(), 0f)
                            p.quadTo(
                                mWidth - arcWidth,
                                (mHeight / 2).toFloat(),
                                mWidth.toFloat(),
                                mHeight.toFloat()
                            )
                            p.close()
                            path.moveTo(0f, 0f)
                            path.lineTo(mWidth.toFloat(), 0f)
                            path.quadTo(
                                mWidth - arcWidth,
                                (mHeight / 2).toFloat(),
                                mWidth.toFloat(),
                                mHeight.toFloat()
                            )
                            path.lineTo(0f, mHeight.toFloat())
                            path.close()
                        }
                    }
                    Gravity.END, Gravity.RIGHT -> {
                        arcPath?.let { p ->
                            p.moveTo(0f, 0f)
                            p.quadTo(arcWidth, (mHeight / 2).toFloat(), 0f, mHeight.toFloat())
                            p.close()
                            path.moveTo(mWidth.toFloat(), 0f)
                            path.lineTo(0f, 0f)
                            path.quadTo(arcWidth, (mHeight / 2).toFloat(), 0f, mHeight.toFloat())
                            path.lineTo(mWidth.toFloat(), mHeight.toFloat())
                            path.close()
                        }
                    }
                    else -> {
                        //do nothing
                    }
                }
            } else {
                when (layoutParams.gravity) {
                    Gravity.START, Gravity.LEFT -> {
                        arcPath?.let { p ->
                            p.moveTo(mWidth - arcWidth / 2, 0f)
                            p.quadTo(
                                mWidth + arcWidth / 2,
                                (mHeight / 2).toFloat(),
                                mWidth - arcWidth / 2,
                                mHeight.toFloat()
                            )
                            p.close()
                            path.moveTo(0f, 0f)
                            path.lineTo(mWidth - arcWidth / 2, 0f)
                            path.quadTo(
                                mWidth + arcWidth / 2,
                                (mHeight / 2).toFloat(),
                                mWidth - arcWidth / 2,
                                mHeight.toFloat()
                            )
                            path.lineTo(0f, mHeight.toFloat())
                            path.close()
                        }
                    }
                    Gravity.END, Gravity.RIGHT -> {
                        arcPath?.let { p ->
                            p.moveTo(arcWidth / 2, 0f)
                            p.quadTo(
                                -arcWidth / 2,
                                (mHeight / 2).toFloat(),
                                arcWidth / 2,
                                mHeight.toFloat()
                            )
                            p.close()
                            path.moveTo(mWidth.toFloat(), 0f)
                            path.lineTo(arcWidth / 2, 0f)
                            path.quadTo(
                                -arcWidth / 2,
                                (mHeight / 2).toFloat(),
                                arcWidth / 2,
                                mHeight.toFloat()
                            )
                            path.lineTo(mWidth.toFloat(), mHeight.toFloat())
                            path.close()
                        }
                    }
                    else -> {
                        //do nothing
                    }
                }
            }
        }

        return path
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            calculateLayoutAndChildren()
        }
    }

    override fun measureChild(
        child: View,
        parentWidthMeasureSpec: Int,
        parentHeightMeasureSpec: Int
    ) {
        if (child is NavigationMenuView) {
            child.measure(
                MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
            )
        } else {
            super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec)
        }
    }

    private fun calculateLayoutAndChildren() {
        settings?.let { s ->
            mHeight = measuredHeight
            mWidth = measuredWidth
            if (mWidth > 0 && mHeight > 0) {
                clipPath = createClipPath()
                ViewCompat.setElevation(this, s.elevation)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View, outline: Outline) {
                        clipPath?.let {
                            if (it.isConvex) {
                                outline.setConvexPath(it)
                            }
                        }
                    }
                }
                val count = childCount
                for (i in 0 until count) {
                    val v = getChildAt(i)
                    if (v is NavigationMenuView) {
                        v.setBackground(s.backgroundDrawable)
                        ViewCompat.setElevation(v, s.elevation)
                        //check: adjusting child views to new width in their rightmost/leftmost points related to path
//                    adjustChildViews((ViewGroup) v)
                    }
                }
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    @Suppress("unused")
    private fun adjustChildViews(container: ViewGroup) {
        val containerChildCount = container.childCount
        val pathMeasure = PathMeasure(arcPath, false)
        val layoutParams = layoutParams as DrawerLayout.LayoutParams
        for (i in 0 until containerChildCount) {
            val child = container.getChildAt(i)
            if (child is ViewGroup) {
                adjustChildViews(container = child)
            } else {
                val pathCenterPointForItem = floatArrayOf(0f, 0f)
                val location = locateView(child)
                val halfHeight = location.height() / 2
                pathMeasure.getPosTan(
                    (location.top + halfHeight).toFloat(),
                    pathCenterPointForItem,
                    null
                )
                if (layoutParams.gravity == Gravity.END || layoutParams.gravity == Gravity.RIGHT) {
                    val centerPathPoint = measuredWidth - pathCenterPointForItem[0].roundToInt()
                    if (child.measuredWidth > centerPathPoint) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(
                                centerPathPoint - THRESHOLD,
                                MeasureSpec.EXACTLY
                            ),
                            MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
                        )
                        child.layout(
                            centerPathPoint + THRESHOLD,
                            child.top,
                            child.right,
                            child.bottom
                        )
                    }
                } else if (layoutParams.gravity == Gravity.START || layoutParams.gravity == Gravity.LEFT) {
                    if (child.measuredWidth > pathCenterPointForItem[0]) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(
                                pathCenterPointForItem[0].roundToInt() - THRESHOLD,
                                MeasureSpec.EXACTLY
                            ),
                            MeasureSpec.makeMeasureSpec(child.measuredHeight, MeasureSpec.EXACTLY)
                        )
                        child.layout(
                            child.left,
                            child.top,
                            pathCenterPointForItem[0].roundToInt() - THRESHOLD,
                            child.bottom
                        )
                    }
                }
                //set text ellipsize to end to prevent it from overlapping edge
                if (child is TextView) {
                    child.ellipsize = TextUtils.TruncateAt.END
                }
            }
        }
    }

    private fun locateView(view: View?): Rect {
        val loc = Rect()
        val location = IntArray(2)
        if (view == null) {
            return loc
        }
        view.getLocationOnScreen(location)
        loc.left = location[0]
        loc.top = location[1]
        loc.right = loc.left + view.width
        loc.bottom = loc.top + view.height
        return loc
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        clipPath?.let {
            canvas.clipPath(it)
        }
        super.dispatchDraw(canvas)
        canvas.restore()
    }

}
