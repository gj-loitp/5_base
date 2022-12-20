package com.loitpcore.animation.flySchool

interface ShapeSetter {
    fun setShape(drawable: Int)

    fun setShape(
        imgObject: ImgObject,
        drawableRes: Int
    )
}
