package com.loitp.views.loading.circularProgressIndicator

import android.animation.Animator

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal open class DefaultAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator, isReverse: Boolean) {}
    override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {}
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}
