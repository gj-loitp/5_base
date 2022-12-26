package com.loitp.views.smoothTransition

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.loitp.views.smoothTransition.ViewUtils.init
import com.loitp.views.smoothTransition.ViewUtils.screenWidth
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// https://github.com/dkmeteor/SmoothTransition
class SwitchAnimationUtil {

    enum class AnimationType {
        ALPHA, ROTATE, HORIZION_LEFT, HORIZION_RIGHT, HORIZON_CROSS, SCALE, FLIP_HORIZON, FLIP_VERTICAL
    }

    private var mOrderIndex = 0
    private val mDelay = 100
    private val mDuration = 100

    fun startAnimation(
        root: View,
        type: AnimationType
    ) {
        init(root.context)
        bindAnimation(root, 0, type)
    }

    private fun bindAnimation(
        view: View,
        depth: Int,
        type: AnimationType
    ) {
        if (view is ViewGroup) {
            if (type == AnimationType.HORIZON_CROSS) {
                /*
                 * Something wrong with it... Fixing
                 */
                for (i in 0 until view.childCount) {
                    bindAnimation(
                        view = view.getChildAt(i),
                        depth = depth + 1,
                        type = if (i % 2 == 0) {
                            AnimationType.HORIZION_LEFT
                        } else {
                            AnimationType.HORIZION_RIGHT
                        }
                    )
                }
            } else {
                for (i in 0 until view.childCount) {
                    bindAnimation(
                        view = view.getChildAt(i),
                        depth = depth + 1,
                        type = type
                    )
                }
            }
        } else {
            runAnimation(
                view = view,
                delay = (mDelay * mOrderIndex).toLong(),
                type = type
            )
            mOrderIndex++
        }
    }

    private fun runAnimation(
        view: View,
        delay: Long,
        type: AnimationType
    ) {
        when (type) {
            AnimationType.ROTATE -> runRotateAnimation(view = view, delay = delay)
            AnimationType.ALPHA -> runAlphaAnimation(view = view, delay = delay)
            AnimationType.HORIZION_LEFT -> runHorizonLeftAnimation(view = view, delay = delay)
            AnimationType.HORIZION_RIGHT -> runHorizonRightAnimation(view = view, delay = delay)
            AnimationType.HORIZON_CROSS -> {
            }
            AnimationType.SCALE -> runScaleAnimation(view = view, delay = delay)
            AnimationType.FLIP_HORIZON -> runFlipHorizonAnimation(view = view, delay = delay)
            AnimationType.FLIP_VERTICAL -> runFlipVertialAnimation(view = view, delay = delay)
        }
    }

    private fun runHorizonLeftAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "translationX",
            -screenWidth.toFloat(),
            0f
        )
        objectAnimator.interpolator = LinearInterpolator()
        val objectAnimatorAlpha = ObjectAnimator.ofFloat(
            view,
            "alpha",
            0f,
            1f
        )
        val animatorSet = AnimatorSet()
        animatorSet.duration = mDuration.toLong()
        animatorSet.startDelay = delay
        animatorSet.playTogether(objectAnimator, objectAnimatorAlpha)
        animatorSet.start()
    }

    private fun runHorizonRightAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "translationX",
            screenWidth.toFloat(),
            0f
        )
        objectAnimator.interpolator = LinearInterpolator()
        val objectAnimatorAlpha = ObjectAnimator.ofFloat(
            view,
            "alpha",
            0f,
            1f
        )
        val animatorSet = AnimatorSet()
        animatorSet.startDelay = delay
        animatorSet.duration = mDuration.toLong()
        animatorSet.playTogether(objectAnimator, objectAnimatorAlpha)
        animatorSet.start()
    }

    private fun runAlphaAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        objectAnimator.startDelay = delay
        objectAnimator.duration = mDuration.toLong()
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }

    private fun runRotateAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator = ObjectAnimator.ofFloat(
            view,
            "rotation",
            0f,
            360f
        )
        val objectAnimator2 = ObjectAnimator.ofFloat(
            view,
            "scaleX",
            0f,
            1f
        )
        val objectAnimator3 = ObjectAnimator.ofFloat(
            view,
            "scaleY",
            0f,
            1f
        )
        val objectAnimator4 = ObjectAnimator.ofFloat(
            view,
            "alpha",
            0f,
            1f
        )
        objectAnimator2.interpolator = AccelerateInterpolator(1.0f)
        objectAnimator3.interpolator = AccelerateInterpolator(1.0f)
        set.duration = mDuration.toLong()
        set.playTogether(
            objectAnimator,
            objectAnimator2,
            objectAnimator3,
            objectAnimator4
        )
        set.startDelay = delay
        set.start()
    }

    private fun runScaleAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        val objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        val objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        set.duration = mDuration.toLong()
        set.playTogether(
            objectAnimator2,
            objectAnimator3,
            objectAnimator4
        )
        set.startDelay = delay
        set.start()
    }

    private fun runFlipVertialAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator1 = ObjectAnimator.ofFloat(view, "rotationX", -180f, 0f)
        val objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        set.duration = mDuration.toLong()
        set.playTogether(
            objectAnimator1,
            objectAnimator2
        )
        set.startDelay = delay
        set.start()
    }

    private fun runFlipHorizonAnimation(
        view: View,
        delay: Long
    ) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator1 = ObjectAnimator.ofFloat(view, "rotationY", -180f, 0f)
        val objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        set.duration = mDuration.toLong()
        set.playTogether(
            objectAnimator1,
            objectAnimator2
        )
        set.startDelay = delay
        set.start()
    }
}
