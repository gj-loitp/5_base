package com.views.progressloadingview.circularprogressindicator

import android.animation.Animator

/**
 * Created by Anton on 17.03.2018.
 */
internal open class DefaultAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator, isReverse: Boolean) {}
    override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {}
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}
