package com.core.utilities

import android.content.Context
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
            play(view, 500, 1, techniques, 0, null)
        }

        fun playRepeatCount(view: View?, techniques: Techniques, count: Int) {
            play(view, 500, count, techniques, 0, null)
        }

        fun play(view: View?, techniques: Techniques, delayInMls: Int) {
            play(view, 500, 1, techniques, delayInMls, null)
        }

        fun play(view: View?, techniques: Techniques, callbackAnimation: CallbackAnimation) {
            play(view, 500, 1, techniques, 0, callbackAnimation)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int) {
            play(view, duration, 1, techniques, 0, null)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int, callbackAnimation: CallbackAnimation) {
            play(view, duration, 1, techniques, 0, callbackAnimation)
        }

        fun playRotate(view: View?, animationListener: Animation.AnimationListener) {
            view?.let {
                val anim = RotateAnimation(0.0f, 90.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                anim.interpolator = LinearInterpolator()
                anim.fillAfter = true
                //anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
                anim.duration = 500 //Put desired duration per anim cycle here, in milliseconds
                anim.setAnimationListener(animationListener)
                it.startAnimation(anim)
            }
        }

        fun slideInDown(context: Context, view: View?) {
            val slideDown = AnimationUtils.loadAnimation(context, R.anim.l_slide_down)
            view?.startAnimation(slideDown)
        }

        fun slideInUp(context: Context, view: View?) {
            val slideDown = AnimationUtils.loadAnimation(context, R.anim.l_slide_up)
            view?.startAnimation(slideDown)
        }

        fun playAnimRandomDuration(viewToAnimate: View) {
            val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = Random.nextLong(501) //to make duration random number between [0,500)
//            anim.duration = 500 //to make duration random number between [0,500)
            viewToAnimate.startAnimation(anim)
        }
    }
}
