package com.loitp.views.layout.aspectRatio

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import com.loitpcore.R
import kotlin.math.roundToInt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LAspectRatioLayout : FrameLayout {

    private var widthRatio = 0f
    private var heightRatio = 0f

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    @Suppress("unused")
    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LAspectRatioLayout,
            defStyleAttr,
            defStyleRes
        )
        widthRatio = typedArray.getFloat(R.styleable.LAspectRatioLayout_arl_widthRatio, 1f)
        heightRatio = typedArray.getFloat(R.styleable.LAspectRatioLayout_arl_heightRatio, 1f)
        typedArray.recycle()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        var widthMeasureSpecFinal = widthMeasureSpec
        var heightMeasureSpecFinal = heightMeasureSpec
        var width = MeasureSpec.getSize(widthMeasureSpecFinal)
        var height = MeasureSpec.getSize(heightMeasureSpecFinal)
        var widthMode = MeasureSpec.getMode(widthMeasureSpecFinal)
        var heightMode = MeasureSpec.getMode(heightMeasureSpecFinal)
        when {
            widthMode == MeasureSpec.EXACTLY -> {
                height = (heightRatio / widthRatio * width).roundToInt()
                heightMode = MeasureSpec.EXACTLY
            }
            heightMode == MeasureSpec.EXACTLY -> {
                width = (widthRatio / heightRatio * height).roundToInt()
                widthMode = MeasureSpec.EXACTLY
            }
        }
        widthMeasureSpecFinal = MeasureSpec.makeMeasureSpec(width, widthMode)
        heightMeasureSpecFinal = MeasureSpec.makeMeasureSpec(height, heightMode)
        super.onMeasure(widthMeasureSpecFinal, heightMeasureSpecFinal)
    }

    val aspectRatio: Float
        get() = widthRatio / heightRatio

    @Suppress("unused")
    fun setAspectRatio(widthRatio: Float, heightRatio: Float) {
        this.widthRatio = widthRatio
        this.heightRatio = heightRatio
        requestLayout()
    }
}
