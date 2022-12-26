package com.loitp.views.draggableFlipView

import android.animation.ValueAnimator
import android.view.View

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FlipListener(
    private var mFrontView: View?,
    private var mBackView: View?,
    private val mParentView: View
) : ValueAnimator.AnimatorUpdateListener {

    private var mFlipped = false
    private var mDirection = 0

    override fun onAnimationUpdate(animation: ValueAnimator) {
        val value = animation.animatedFraction
        val scaleValue = 0.625f + 1.5f * (value - 0.5f) * (value - 0.5f)
        if (value <= 0.5f) {
            mParentView.rotationY = 180 * value * mDirection
            if (mFlipped) setStateFlipped(false)
        } else {
            mParentView.rotationY = -180 * (1 - value) * mDirection
            if (!mFlipped) setStateFlipped(true)
        }
        mParentView.scaleX = scaleValue
        mParentView.scaleY = scaleValue
    }

    fun reverse() {
        val temp = mBackView
        mBackView = mFrontView
        mFrontView = temp
    }

    fun setRotateDirection(direction: Int) {
        mDirection = direction
    }

    private fun setStateFlipped(flipped: Boolean) {
        mFlipped = flipped
        mFrontView?.visibility = if (flipped) View.GONE else View.VISIBLE
        mBackView?.visibility = if (flipped) View.VISIBLE else View.GONE
    }

    init {
        mBackView?.visibility = View.GONE
    }
}
