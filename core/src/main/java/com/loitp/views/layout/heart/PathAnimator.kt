package com.loitp.views.layout.heart

import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class PathAnimator(config: Config) : AbstractPathAnimator(config) {

    companion object {
        private fun scale(a: Double, b: Double, c: Double, d: Double, e: Double): Float {
            return ((a - b) / (c - b) * (e - d) + d).toFloat()
        }
    }

    private val atomicInteger = AtomicInteger(0)
    private val mHandler: Handler = Handler(Looper.getMainLooper())

    override fun start(
        child: View,
        parent: ViewGroup
    ) {
        parent.addView(child, ViewGroup.LayoutParams(mConfig.heartWidth, mConfig.heartHeight))

        val anim = FloatAnimation(
            path = createPath(
                counter = atomicInteger,
                view = parent,
                factor = 2
            ),
            rotation = randomRotation(),
            parent = parent,
            child = child
        )
        anim.duration = mConfig.animDuration.toLong()
        anim.interpolator = LinearInterpolator()

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                mHandler.post {
                    parent.removeView(child)
                }
                atomicInteger.decrementAndGet()
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {
                atomicInteger.incrementAndGet()
            }
        })
        anim.interpolator = LinearInterpolator()
        child.startAnimation(anim)
    }

    internal class FloatAnimation(
        path: Path?,
        rotation: Float,
        parent: View?,
        child: View?
    ) :
        Animation() {
        private val pathMeasure: PathMeasure = PathMeasure(path, false)
        private val mView: View? = child
        private val mDistance: Float = pathMeasure.length
        private val mRotation: Float = rotation

        override fun applyTransformation(factor: Float, transformation: Transformation) {
            val matrix = transformation.matrix
            pathMeasure.getMatrix(mDistance * factor, matrix, PathMeasure.POSITION_MATRIX_FLAG)
            mView?.rotation = mRotation * factor
            var scale = 1f
            if (3000.0f * factor < 200.0f) {
                scale = scale(
                    factor.toDouble(),
                    0.0,
                    0.06666667014360428,
                    0.20000000298023224,
                    1.100000023841858
                )
            } else if (3000.0f * factor < 300.0f) {
                scale = scale(
                    factor.toDouble(),
                    0.06666667014360428,
                    0.10000000149011612,
                    1.100000023841858,
                    1.0
                )
            }
            mView?.scaleX = scale
            mView?.scaleY = scale
            transformation.alpha = 1.0f - factor
        }

        init {
            parent?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
    }
}
