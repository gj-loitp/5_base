package com.views.progressloadingview.window

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import com.views.progressloadingview.window.Utils.px2dp

class Base10Indicator(
        context: Context,
        indicatorHeight: Int,
        private val color: Int,
        radius: Int
) : View(context) {

    init {
        initialize(indicatorHeight, radius)
    }

    private fun initialize(indicatorHeight: Int, radius: Int) {
        this.background = getCube(radius)
        val lp = LinearLayout.LayoutParams(
                px2dp(context = context, px = indicatorHeight),
                px2dp(context = context, px = indicatorHeight)
        )
        layoutParams = lp
    }

    private fun getCube(radius: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.cornerRadius = px2dp(context = context, px = radius).toFloat()
        return drawable
    }

}
