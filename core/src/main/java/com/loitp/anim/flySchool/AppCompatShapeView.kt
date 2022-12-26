package com.loitp.anim.flySchool

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.loitp.R
import com.loitp.anim.flySchool.Utils.setHeart

class AppCompatShapeView : AppCompatImageView, ShapeSetter {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setImageResource(R.drawable.l_facebook_button_blue)
    }

    override fun setShape(drawable: Int) {
        setImageResource(drawable)
        setHeart(this)
    }

    override fun setShape(
        imgObject: ImgObject,
        drawableRes: Int
    ) {
        // setImageResource(R.mipmap.ic_launcher);
    }
}
