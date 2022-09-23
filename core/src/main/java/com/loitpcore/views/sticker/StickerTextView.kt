package com.loitpcore.views.sticker

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class StickerTextView : StickerView {

//    companion object {
//        fun pixelsToSp(
//            context: Context,
//            px: Float
//        ): Float {
//            val scaledDensity = context.resources.displayMetrics.scaledDensity
//            return px / scaledDensity
//        }
//    }

    private var tvMain: AutoResizeTextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private fun getView(): View {
        if (tvMain != null) {
            return tvMain!!
        }

        tvMain = AutoResizeTextView(context)
        tvMain?.apply {
            this.setTextColor(Color.WHITE)
            this.gravity = Gravity.CENTER
            this.textSize = 400f
            this.setShadowLayer(4f, 0f, 0f, Color.BLACK)
            this.maxLines = 1
            val params = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            params.gravity = Gravity.CENTER
            this.layoutParams = params
        }

        imageViewFlip?.visibility = GONE
        return tvMain!!
    }

    var text: String?
        get() = if (tvMain != null) {
            tvMain?.text.toString()
        } else {
            null
        }
        set(text) {
            tvMain?.text = text
        }
    override val mainView: View
        get() = getView()
}
