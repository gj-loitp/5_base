package com.core.utilities

import android.view.View
import android.view.animation.*
import com.R
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.interfaces.CallbackAnimation
import kotlin.random.Random

/**
 * Created by www.muathu@gmail.com on 6/9/2017.
 */

class LAnimationUtil {

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 300//mls

        fun play(view: View?, duration: Int, repeatCount: Int, techniques: Techniques, delayInMls: Int, callbackAnimation: CallbackAnimation?) {
            if (view == null) {
                return
            }

            view.clearAnimation()
            YoYo.with(techniques)
                    .duration(duration.toLong())
                    .repeat(repeatCount)
                    .onCancel {
                        callbackAnimation?.onCancel()
                    }
                    .onEnd {
                        callbackAnimation?.onEnd()
                    }
                    .onRepeat {
                        callbackAnimation?.onRepeat()
                    }
                    .onStart {
                        callbackAnimation?.onStart()
                    }
                    .delay(delayInMls.toLong())
                    .playOn(view)
        }

        fun play(view: View?, techniques: Techniques) {
            play(view = view, duration = DEFAULT_ANIMATION_DURATION, repeatCount = 0, techniques = techniques, delayInMls = 0, callbackAnimation = null)
        }

        fun playRepeatCount(view: View?, techniques: Techniques, count: Int) {
            play(view = view, duration = DEFAULT_ANIMATION_DURATION, repeatCount = count, techniques = techniques, delayInMls = 0, callbackAnimation = null)
        }

        fun play(view: View?, techniques: Techniques, delayInMls: Int) {
            play(view = view, duration = DEFAULT_ANIMATION_DURATION, repeatCount = 0, techniques = techniques, delayInMls = delayInMls, callbackAnimation = null)
        }

        fun play(view: View?, techniques: Techniques, callbackAnimation: CallbackAnimation) {
            play(view = view, duration = DEFAULT_ANIMATION_DURATION, repeatCount = 0, techniques = techniques, delayInMls = 0, callbackAnimation = callbackAnimation)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int) {
            play(view = view, duration = duration, repeatCount = 0, techniques = techniques, delayInMls = 0, callbackAnimation = null)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int, callbackAnimation: CallbackAnimation) {
            play(view = view, duration = duration, repeatCount = 0, techniques = techniques, delayInMls = 0, callbackAnimation = callbackAnimation)
        }

        fun playRotate(view: View?, animationListener: Animation.AnimationListener) {
            view?.let {
                val anim = RotateAnimation(0.0f, 90.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f)
                anim.interpolator = LinearInterpolator()
                anim.fillAfter = true
                //anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
                anim.duration = DEFAULT_ANIMATION_DURATION.toLong() //Put desired duration per anim cycle here, in milliseconds
                anim.setAnimationListener(animationListener)
                it.startAnimation(anim)
            }
        }

        fun slideInDown(view: View?) {
            view?.let {
                val slideDown = AnimationUtils.loadAnimation(it.context, R.anim.l_slide_down)
                it.startAnimation(slideDown)
            }
        }

        fun slideInUp(view: View?) {
            view?.let {
                val slideDown = AnimationUtils.loadAnimation(it.context, R.anim.l_slide_up)
                it.startAnimation(slideDown)
            }
        }

        fun playAnimRandomDuration(viewToAnimate: View) {
            val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = Random.nextLong(501) //to make duration random number between [0,500)
//            anim.duration = 500 //to make duration random number between [0,500)
            viewToAnimate.startAnimation(anim)
        }
    }
}
