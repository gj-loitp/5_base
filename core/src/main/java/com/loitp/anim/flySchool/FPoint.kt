package com.loitp.anim.flySchool

/**
 * Point class that takes everything with respect to the width and height
 * of the ShapeFlyer [ShapeFlyer] view
 * e.g. if it is given X = 0.5f and Y = 0.5f
 * It actially means : X = 0.5w and Y = 0.5h
 *
 */
class FPoint(
    private val mX: Float,
    private val mY: Float
) {
    fun getmX(): Float {
        return mX
    }

    fun getmY(): Float {
        return mY
    }
}
