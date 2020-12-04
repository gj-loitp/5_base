package com.views.sticker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class StickerImageView : StickerView {

    var ownerId: String? = null
    private var ivMain: ImageView? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    public override fun getMainView(): View {
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
}
