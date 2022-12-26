package com.loitp.anim.flySchool

import android.graphics.Path

class BeizerPath(
    private val mFPoint1: FPoint,
    private val mFPoint2: FPoint,
    private val mFPoint3: FPoint
) : FlyPath() {

    private var mPath: Path? = null

    private fun getmFPoint1(): FPoint {
        return mFPoint1
    }

    private fun getmFPoint2(): FPoint {
        return mFPoint2
    }

    private fun getmFPoint3(): FPoint {
        return mFPoint3
    }

    override fun getPath(
        mOrigin: FPoint,
        width: Float,
        height: Float
    ): Path {

        if (mPath == null) {
            mPath = Path()
            mPath?.moveTo(mOrigin.getmX() * width, mOrigin.getmY() * height)

            val x1: Float = getmFPoint1().getmX() * width
            val y1: Float = getmFPoint1().getmY() * height
            val x2: Float = getmFPoint2().getmX() * width
            val y2: Float = getmFPoint2().getmY() * height
            val x3: Float = getmFPoint3().getmX() * width
            val y3: Float = getmFPoint3().getmY() * height

            mPath?.rCubicTo(x1, y1, x2, y2, x3, y3)
        }

        return mPath!!
    }
}
