package com.loitp.anim.flySchool

import android.graphics.Path

abstract class FlyPath {
    /**
     * @param mOrigin : Origin point where the PATH should start
     * @param width   : Width of the [ShapeFlyer] in pixels
     * @param height  : Height of the [ShapeFlyer] in pixels
     *
     *
     * Should return the path/trajectory of the animation.
     * Please refer to [BeizerPath] and [SingleLinePath] for examples
     */
    abstract fun getPath(
        mOrigin: FPoint,
        width: Float,
        height: Float
    ): Path

    companion object {
        fun getSimpleLinePath(mFPoint: FPoint): SingleLinePath {
            return SingleLinePath(mFPoint)
        }

        fun getBeizerPath(
            mFPoint1: FPoint,
            mFPoint2: FPoint,
            mFPoint3: FPoint
        ): BeizerPath {
            return BeizerPath(mFPoint1, mFPoint2, mFPoint3)
        }

        @Suppress("unused")
        fun getMultipleLinePath(vararg mFPoints: FPoint?): MultipleLinePath? {
            var multipleLinePath: MultipleLinePath? = null
            if (mFPoints.isNotEmpty()) {
                multipleLinePath = MultipleLinePath()
                for (fPoint in mFPoints) {
                    multipleLinePath.addFPoint(fPoint)
                }
            }
            return multipleLinePath
        }
    }
}
