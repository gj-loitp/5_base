package com.animation.flyschool

/**
 * Created by avin on 31/01/17.
 */
interface ShapeSetter {
    fun setShape(drawable: Int)
    fun setShape(imgObject: ImgObject, drawableRes: Int)
}
