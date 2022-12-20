package com.loitp.animation.flySchool

interface ShapeSetter {
    fun setShape(drawable: Int)

    fun setShape(
        imgObject: ImgObject,
        drawableRes: Int
    )
}
