package com.views.layout.heartlayout

import android.content.res.TypedArray
import android.graphics.Path
import android.view.View
import android.view.ViewGroup
import com.R
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

abstract class AbstractPathAnimator(
    val mConfig: Config
) {
    private val mRandom: Random = Random()

    fun randomRotation(): Float {
        return mRandom.nextFloat() * 28.6f - 14.3f
    }

    @Suppress("NAME_SHADOWING")
    fun createPath(counter: AtomicInteger, view: View, factor: Int): Path {
        var factor = factor
        val r = mRandom
        var x = r.nextInt(mConfig.xRand)
        var x2 = r.nextInt(mConfig.xRand)
        val y = view.height - mConfig.initY
        var y2 =
            counter.toInt() * 15 + mConfig.animLength * factor + r.nextInt(mConfig.animLengthRand)
        factor = y2 / mConfig.bezierFactor
        x += mConfig.xPointFactor
        x2 += mConfig.xPointFactor
        val y3 = y - y2
        y2 = y - y2 / 2
        val p = Path()
        p.moveTo(mConfig.initX.toFloat(), y.toFloat())
        p.cubicTo(
            mConfig.initX.toFloat(),
            (y - factor).toFloat(),
            x.toFloat(),
            (y2 + factor).toFloat(),
            x.toFloat(),
            y2.toFloat()
        )
        p.moveTo(x.toFloat(), y2.toFloat())
        p.cubicTo(
            x.toFloat(),
            (y2 - factor).toFloat(),
            x2.toFloat(),
            (y3 + factor).toFloat(),
            x2.toFloat(),
            y3.toFloat()
        )
        return p
    }

    abstract fun start(child: View, parent: ViewGroup)

    class Config {
        var initX = 0
        var initY = 0
        var xRand = 0
        var animLengthRand = 0
        var bezierFactor = 0
        var xPointFactor = 0
        var animLength = 0

        @JvmField
        var heartWidth = 0

        @JvmField
        var heartHeight = 0

        @JvmField
        var animDuration = 0

        companion object {
            @JvmStatic
            fun fromTypeArray(typedArray: TypedArray): Config {
                val config = Config()
                val res = typedArray.resources
                config.initX = typedArray.getDimension(
                    R.styleable.LHeartLayout_initX,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_init_x).toFloat()
                ).toInt()
                config.initY = typedArray.getDimension(
                    R.styleable.LHeartLayout_initY,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_init_y).toFloat()
                ).toInt()
                config.xRand = typedArray.getDimension(
                    R.styleable.LHeartLayout_xRand,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_bezier_x_rand).toFloat()
                ).toInt()
                config.animLength = typedArray.getDimension(
                    R.styleable.LHeartLayout_animLength,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_length).toFloat()
                ).toInt()
                config.animLengthRand = typedArray.getDimension(
                    R.styleable.LHeartLayout_animLengthRand,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_length_rand).toFloat()
                ).toInt()
                config.bezierFactor = typedArray.getInteger(
                    R.styleable.LHeartLayout_bezierFactor,
                    res.getInteger(R.integer.heart_anim_bezier_factor)
                )
                config.xPointFactor = typedArray.getDimension(
                    R.styleable.LHeartLayout_xPointFactor,
                    res.getDimensionPixelOffset(R.dimen.heart_anim_x_point_factor).toFloat()
                ).toInt()
                config.heartWidth = typedArray.getDimension(
                    R.styleable.LHeartLayout_heart_width,
                    res.getDimensionPixelOffset(R.dimen.heart_size_width).toFloat()
                ).toInt()
                config.heartHeight = typedArray.getDimension(
                    R.styleable.LHeartLayout_heart_height,
                    res.getDimensionPixelOffset(R.dimen.heart_size_height).toFloat()
                ).toInt()
                config.animDuration = typedArray.getInteger(
                    R.styleable.LHeartLayout_anim_duration,
                    res.getInteger(R.integer.anim_duration)
                )
                return config
            }
        }
    }
}
