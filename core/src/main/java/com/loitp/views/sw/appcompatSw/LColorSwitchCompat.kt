package com.loitp.views.sw.appcompatSw

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LColorSwitchCompat : SwitchCompat {

    var toggleOnColor = Color.parseColor("#009284")
    var toggleOffColor = Color.parseColor("#ececec")
    var bgOnColor = Color.parseColor("#97d9d7")
    var bgOffColor = Color.parseColor("#a6a6a6")

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        applyAttributes(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        applyAttributes(context, attrs)
    }

    private fun applyAttributes(
        context: Context,
        attrs: AttributeSet
    ) {
        // Extract attrs
        val a = context.obtainStyledAttributes(attrs, R.styleable.LColorSwitchCompat)
        val indexCount = a.indexCount
        for (i in 0 until indexCount) {
            when (val attr = a.getIndex(i)) {
                R.styleable.LColorSwitchCompat_toggleOnColor ->
                    toggleOnColor = a.getColor(attr, Color.parseColor("#009284"))
                R.styleable.LColorSwitchCompat_toggleOffColor ->
                    toggleOffColor = a.getColor(attr, Color.parseColor("#ececec"))
                R.styleable.LColorSwitchCompat_bgOnColor ->
                    bgOnColor = a.getColor(attr, Color.parseColor("#97d9d7"))
                R.styleable.LColorSwitchCompat_bgOffColor ->
                    bgOffColor = a.getColor(attr, Color.parseColor("#a6a6a6"))
            }
        }
        a.recycle()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()

        if (this.isChecked) {
            // Checked
            DrawableCompat.setTint(this.thumbDrawable, toggleOnColor)
            DrawableCompat.setTint(this.trackDrawable, bgOnColor)
        } else {
            // Not checked
            DrawableCompat.setTint(this.thumbDrawable, toggleOffColor)
            DrawableCompat.setTint(this.trackDrawable, bgOffColor)
        }
        requestLayout()
        invalidate()
    }

}