package com.loitpcore.views.wwlMusic.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.loitpcore.R

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LWWLMusicFixedAspectRatioRelativeLayout : RelativeLayout {
    private var mAspectRatio: Float

    constructor(context: Context) : super(context) {
        mAspectRatio = 1.0f
    }

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatio)
        mAspectRatio = typedArray.getFraction(R.styleable.FixedAspectRatio_aspectRatio, 1, 1, 1.0f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (MeasureSpec.EXACTLY) {
            MeasureSpec.getMode(widthMeasureSpec) -> {
                super.onMeasure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(
                        (MeasureSpec.getSize(widthMeasureSpec).toFloat() / mAspectRatio).toInt(),
                        MeasureSpec.EXACTLY
                    )
                )
            }
            MeasureSpec.getMode(heightMeasureSpec) -> {
                super.onMeasure(
                    MeasureSpec.makeMeasureSpec(
                        (MeasureSpec.getSize(heightMeasureSpec).toFloat() * mAspectRatio).toInt(),
                        MeasureSpec.EXACTLY
                    ),
                    heightMeasureSpec
                )
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }
}
