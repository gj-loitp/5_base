package com.loitpcore.animation.flySchool

import android.graphics.Path

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
