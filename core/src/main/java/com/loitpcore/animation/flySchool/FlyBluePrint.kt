package com.loitpcore.animation.flySchool

import android.graphics.Path

class FlyBluePrint(
    private val mOrigin: FPoint,
    private val mFlyPath: FlyPath
) {

    @Suppress("unused")
    fun getmFlyPath(): FlyPath {
        return mFlyPath
    }

    @Suppress("unused")
    fun getmOrigin(): FPoint {
        return mOrigin
    }

    fun getPath(
        width: Float,
        height: Float
    ): Path {
        return mFlyPath.getPath(mOrigin = mOrigin, width = width, height = height)
    }
}
