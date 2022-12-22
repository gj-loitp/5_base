package vn.loitp.app.a.cv.indicator.ext.titles

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class ScaleTransitionPagerTitleView(context: Context) : ColorTransitionPagerTitleView(context) {

    private var minScale = 0.75f

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        super.onEnter(index, totalCount, enterPercent, leftToRight)

        scaleX = minScale + (1.0f - minScale) * enterPercent
        scaleY = minScale + (1.0f - minScale) * enterPercent
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        super.onLeave(index, totalCount, leavePercent, leftToRight)

        scaleX = 1.0f + (minScale - 1.0f) * leavePercent
        scaleY = 1.0f + (minScale - 1.0f) * leavePercent
    }
}
