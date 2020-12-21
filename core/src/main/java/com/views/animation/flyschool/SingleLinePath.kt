package com.views.animation.flyschool

import android.graphics.Path

/**
 * Simple line path generator
 * Requires one [FPoint] to generate the Path
 *
 *
 * Created by avin on 20/01/17.
 */
class SingleLinePath(
        private val mFPoint: FPoint
) : FlyPath() {

    private var mPath: Path? = null

    private fun getmFPoint(): FPoint {
        return mFPoint
    }

    override fun getPath(mOrigin: FPoint, width: Float, height: Float): Path {
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
