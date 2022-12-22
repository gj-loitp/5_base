package com.loitp.anim.morphTransitions

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.util.ArrayMap
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.loitp.R

internal object AnimUtils {
    private var fastOutSlowIn: Interpolator? = null
    private var fastOutLinearIn: Interpolator? = null
    private var linearOutSlowIn: Interpolator? = null

    @JvmStatic
    fun getFastOutSlowInInterpolator(context: Context?): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn =
                AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_slow_in)
        }
        return fastOutSlowIn
    }

    @JvmStatic
    fun getFastOutLinearInInterpolator(context: Context?): Interpolator? {
        if (fastOutLinearIn == null) {
            fastOutLinearIn =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_linear_in)
        }
        return fastOutLinearIn
    }

    @JvmStatic
    fun getLinearOutSlowInInterpolator(context: Context?): Interpolator? {
        if (linearOutSlowIn == null) {
            linearOutSlowIn =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in)
        }
        return linearOutSlowIn
    }

    /**
     * https://halfthought.wordpress.com/2014/11/07/reveal-transition/
     *
     *
     * Interrupting Activity transitions can yield an OperationNotSupportedException when the
     * transition tries to pause the animator. Yikes! We can fix this by wrapping the Animator:
     */
    class NoPauseAnimator(private val mAnimator: Animator) : Animator() {

        private val mListeners = ArrayMap<AnimatorListener, AnimatorListener>()

        override fun addListener(listener: AnimatorListener) {
            val wrapper: AnimatorListener = AnimatorListenerWrapper(this, listener)

            if (!mListeners.containsKey(listener)) {
                mListeners[listener] = wrapper
                mAnimator.addListener(wrapper)
            }
        }

        override fun cancel() {
            mAnimator.cancel()
        }

        override fun end() {
            mAnimator.end()
        }

        override fun getDuration(): Long {
            return mAnimator.duration
        }

        override fun getInterpolator(): TimeInterpolator {
            return mAnimator.interpolator
        }

        override fun setInterpolator(timeInterpolator: TimeInterpolator) {
            mAnimator.interpolator = timeInterpolator
        }

        override fun getListeners(): ArrayList<AnimatorListener> {
            return ArrayList(mListeners.keys)
        }

        override fun getStartDelay(): Long {
            return mAnimator.startDelay
        }

        override fun setStartDelay(delayMS: Long) {
            mAnimator.startDelay = delayMS
        }

        override fun isPaused(): Boolean {
            return mAnimator.isPaused
        }

        override fun isRunning(): Boolean {
            return mAnimator.isRunning
        }

        override fun isStarted(): Boolean {
            return mAnimator.isStarted
        }

        /* We don't want to override pause or resume methods because we don't want them
         * to affect mAnimator.
        public void pause();

        public void resume();

        public void addPauseListener(AnimatorPauseListener listener);

        public void removePauseListener(AnimatorPauseListener listener);
        */
        override fun removeAllListeners() {
            mListeners.clear()
            mAnimator.removeAllListeners()
        }

        override fun removeListener(listener: AnimatorListener) {
            val wrapper = mListeners[listener]
            if (wrapper != null) {
                mListeners.remove(listener)
                mAnimator.removeListener(wrapper)
            }
        }

        override fun setDuration(durationMS: Long): Animator {
            mAnimator.duration = durationMS
            return this
        }

        override fun setTarget(target: Any?) {
            mAnimator.setTarget(target)
        }

        override fun setupEndValues() {
            mAnimator.setupEndValues()
        }

        override fun setupStartValues() {
            mAnimator.setupStartValues()
        }

        override fun start() {
            mAnimator.start()
        }
    }

    internal class AnimatorListenerWrapper(
        private val mAnimator: Animator,
        private val mListener: Animator.AnimatorListener
    ) : Animator.AnimatorListener {

        override fun onAnimationStart(animator: Animator) {
            mListener.onAnimationStart(mAnimator)
        }

        override fun onAnimationEnd(animator: Animator) {
            mListener.onAnimationEnd(mAnimator)
        }

        override fun onAnimationCancel(animator: Animator) {
            mListener.onAnimationCancel(mAnimator)
        }

        override fun onAnimationRepeat(animator: Animator) {
            mListener.onAnimationRepeat(mAnimator)
        }
    }
}
