package com.loitp.anim.flySchool

interface ShapeSetter {
    fun setShape(drawable: Int)

    fun setShape(
        imgObject: ImgObject,
        drawableRes: Int
    )
}
