package com.loitp.anim.flySchool

import android.graphics.Path

class MultipleLinePath : FlyPath {
    private var mFPoints = ArrayList<FPoint>()
    private var mPath: Path? = null

    @Suppress("unused")
    constructor(mFPoints: ArrayList<FPoint>) {
        this.mFPoints = mFPoints
    }

    constructor()

    @Suppress("unused")
    fun getmFPoints(): ArrayList<FPoint> {
        return mFPoints
    }

    fun addFPoint(fPoint: FPoint?) {
        if (fPoint == null) {
            return
        }
        mFPoints.add(fPoint)
    }

    override fun getPath(
        mOrigin: FPoint,
        width: Float,
        height: Float
    ): Path {
        if (mPath == null) {
            mPath = Path()
            mPath?.moveTo(mOrigin.getmX() * width, mOrigin.getmY() * height)
            if (mFPoints.size > 0) {
                for (fPoint in mFPoints) {
                    mPath?.lineTo(fPoint.getmX() * width, fPoint.getmY() * height)
                }
            }
        }
        return mPath!!
    }
}
