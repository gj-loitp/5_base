package vn.loitp.app.a.cv.indicator.ext.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

class CommonPagerIndicator(context: Context?) : View(context), IPagerIndicator {

    companion object {
        const val MODE_MATCH_EDGE = 0 // drawable宽度 == title宽度 - 2 * mXOffset
        const val MODE_WRAP_CONTENT = 1 // drawable宽度 == title内容宽度 - 2 * mXOffset
        const val MODE_EXACTLY = 2
    }

    private var mMode = // 默认为MODE_MATCH_EDGE模式
        0
    private var indicatorDrawable: Drawable? = null

    private var startInterpolator: Interpolator = LinearInterpolator()
    private var endInterpolator: Interpolator = LinearInterpolator()
    private var drawableHeight = 0f
    private var drawableWidth = 0f
    private var yOffset = 0f
    private var xOffset = 0f
    private var mPositionDataList = ArrayList<PositionData>()
    private val mDrawableRect = Rect()

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        if (indicatorDrawable == null || mPositionDataList.isEmpty()) {
            return
        }

        val current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position)
        val next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1)
        val leftX: Float
        val nextLeftX: Float
        val rightX: Float
        val nextRightX: Float
        when (mMode) {
            MODE_MATCH_EDGE -> {
                leftX = current.mLeft + xOffset
                nextLeftX = next.mLeft + xOffset
                rightX = current.mRight - xOffset
                nextRightX = next.mRight - xOffset
                mDrawableRect.top = yOffset.toInt()
                mDrawableRect.bottom = (height - yOffset).toInt()
            }
            MODE_WRAP_CONTENT -> {
                leftX = current.mContentLeft + xOffset
                nextLeftX = next.mContentLeft + xOffset
                rightX = current.mContentRight - xOffset
                nextRightX = next.mContentRight - xOffset
                mDrawableRect.top = (current.mContentTop - yOffset).toInt()
                mDrawableRect.bottom = (current.mContentBottom + yOffset).toInt()
            }
            else -> { // MODE_EXACTLY
                leftX = current.mLeft + (current.width() - drawableWidth) / 2
                nextLeftX = next.mLeft + (next.width() - drawableWidth) / 2
                rightX = current.mLeft + (current.width() + drawableWidth) / 2
                nextRightX = next.mLeft + (next.width() + drawableWidth) / 2
                mDrawableRect.top = (height - drawableHeight - yOffset).toInt()
                mDrawableRect.bottom = (height - yOffset).toInt()
            }
        }
        mDrawableRect.left =
            (leftX + (nextLeftX - leftX) * startInterpolator.getInterpolation(positionOffset)).toInt()
        mDrawableRect.right =
            (rightX + (nextRightX - rightX) * endInterpolator.getInterpolation(positionOffset)).toInt()
        indicatorDrawable?.bounds = mDrawableRect
        invalidate()
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onDraw(canvas: Canvas) {
        indicatorDrawable?.draw(canvas)
    }

    override fun onPositionDataProvide(dataList: List<PositionData>) {
        if (dataList is ArrayList) {
            mPositionDataList = dataList
        }
    }

    var mode: Int
        get() = mMode
        set(mode) {
            mMode =
                if (mode == MODE_EXACTLY || mode == MODE_MATCH_EDGE || mode == MODE_WRAP_CONTENT) {
                    mode
                } else {
                    throw IllegalArgumentException("mode $mode not supported.")
                }
        }
}
