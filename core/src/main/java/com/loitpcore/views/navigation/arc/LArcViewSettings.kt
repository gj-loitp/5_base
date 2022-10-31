package com.loitpcore.views.navigation.arc

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LArcViewSettings(context: Context, attrs: AttributeSet?) {

    companion object {
        const val CROP_INSIDE = 0
        @Suppress("unused")
        const val CROP_OUTSIDE = 1

        @JvmStatic
        fun dpToPx(context: Context, dp: Int): Float {
            val resources = context.resources
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                resources.displayMetrics
            )
        }
    }

    var isCropInside = true
    val arcWidth: Float
    var elevation = 0f
    var backgroundDrawable: Drawable? = ColorDrawable(Color.WHITE)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcDrawer, 0, 0)
        arcWidth = typedArray.getDimension(R.styleable.ArcDrawer_arc_width, dpToPx(context, 10))
        val cropDirection = typedArray.getInt(R.styleable.ArcDrawer_arc_cropDirection, CROP_INSIDE)
        isCropInside = cropDirection == CROP_INSIDE
        val attrsArray = intArrayOf(
            android.R.attr.background,
            android.R.attr.layout_gravity
        )
        val androidAttrs = context.obtainStyledAttributes(attrs, attrsArray)
        backgroundDrawable = androidAttrs.getDrawable(0)
        androidAttrs.recycle()
        typedArray.recycle()
    }
}
