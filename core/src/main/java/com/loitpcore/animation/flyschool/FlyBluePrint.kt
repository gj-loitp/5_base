package com.loitpcore.animation.flyschool

import android.graphics.Path

/**
 * Class that defines the Blue Print of the complete Animation
 * Has two members variables that define everything:
 * 1. mOrigin : That defines the origin point of the animation
 * 2. mFlyPath : That defines tha path the object is going to take starting at the mOrigin
 *
 *
 * Refer to the [com.cipherthinkers.shapeflyer.flyschool.FlyPath] for more details
 *
 *
 * Created by avin on 12/01/17.
 */
class FlyBluePrint(
    private val mOrigin: FPoint,
    private val mFlyPath: FlyPath
) {

    fun getmFlyPath(): FlyPath {
        return mFlyPath
    }

    fun getmOrigin(): FPoint {
        return mOrigin
    }

    fun getPath(width: Float, height: Float): Path {
        return mFlyPath.getPath(mOrigin = mOrigin, width = width, height = height)
    }
}
