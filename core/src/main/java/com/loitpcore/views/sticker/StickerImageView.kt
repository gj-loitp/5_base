package com.loitpcore.views.sticker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class StickerImageView : StickerView {

//    var ownerId: String? = null
    private var ivMain: ImageView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private fun getView(): View {
        if (ivMain == null) {
            ivMain = ImageView(context)
            ivMain?.scaleType = ImageView.ScaleType.FIT_XY
        }
        return ivMain!!
    }

    fun setImageResource(resId: Int) {
        ivMain?.setImageResource(resId)
    }

    fun setImageDrawable(drawable: Drawable?) {
        ivMain?.setImageDrawable(drawable)
    }

    var imageBitmap: Bitmap?
        get() = (ivMain?.drawable as BitmapDrawable).bitmap
        set(bmp) {
            ivMain?.setImageBitmap(bmp)
        }
    override val mainView: View
        get() = getView()
}
