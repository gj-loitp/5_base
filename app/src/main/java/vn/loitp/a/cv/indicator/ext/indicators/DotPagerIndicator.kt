package vn.loitp.a.cv.indicator.ext.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

class DotPagerIndicator(context: Context) : View(context), IPagerIndicator {

    private var mDataList = ArrayList<PositionData>()
    private var mRadius: Float
    private var mYOffset: Float
    private var mDotColor: Int
    private var mCircleCenterX = 0f
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onPageSelected(position: Int) {
        if (mDataList.isEmpty()) {
            return
        }
        val data = mDataList[position]
        mCircleCenterX = (data.mLeft + data.width() / 2).toFloat()
        invalidate()
    }

    override fun onPositionDataProvide(dataList: List<PositionData>) {
        if (dataList is ArrayList) {
            mDataList = dataList
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = mDotColor
        canvas.drawCircle(mCircleCenterX, height - mYOffset - mRadius, mRadius, mPaint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            invalidate()
        }

    @Suppress("unused")
    var yOffset: Float
        get() = mYOffset
        set(yOffset) {
            mYOffset = yOffset
            invalidate()
        }

    @Suppress("unused")
    var dotColor: Int
        get() = mDotColor
        set(dotColor) {
            mDotColor = dotColor
            invalidate()
        }

    init {
        mRadius = UIUtil.dip2px(context, 3.0).toFloat()
        mYOffset = UIUtil.dip2px(context, 3.0).toFloat()
        mDotColor = Color.WHITE
    }
}
