package vn.loitp.a.cv.indicator.ext.titles

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class ColorFlipPagerTitleView(context: Context) : SimplePagerTitleView(context) {

    private var changePercent = 0.5f

    override fun onLeave(
        index: Int,
        totalCount: Int,
        leavePercent: Float,
        leftToRight: Boolean
    ) {
        if (leavePercent >= changePercent) {
            setTextColor(mNormalColor)
        } else {
            setTextColor(mSelectedColor)
        }
    }

    override fun onEnter(
        index: Int,
        totalCount: Int,
        enterPercent: Float,
        leftToRight: Boolean
    ) {
        if (enterPercent >= changePercent) {
            setTextColor(mSelectedColor)
        } else {
            setTextColor(mNormalColor)
        }
    }

    override fun onSelected(
        index: Int,
        totalCount: Int
    ) {}

    override fun onDeselected(
        index: Int,
        totalCount: Int
    ) {}
}
