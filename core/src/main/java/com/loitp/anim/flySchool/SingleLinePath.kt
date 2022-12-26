package com.loitp.anim.flySchool

import android.graphics.Path

class SingleLinePath(
    private val mFPoint: FPoint
) : FlyPath() {

    private var mPath: Path? = null

    private fun getmFPoint(): FPoint {
        return mFPoint
    }

    override fun getPath(
        mOrigin: FPoint,
        width: Float,
        height: Float
    ): Path {
        if (mPath == null) {
            mPath = Path()
            mPath?.let {
                it.moveTo(mOrigin.getmX() * width, mOrigin.getmY() * height)
                it.lineTo(getmFPoint().getmX() * width, getmFPoint().getmY() * height)
            }
        }
        return mPath!!
    }
}
