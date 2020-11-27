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

        fun play(
                view: View? = null,
                duration: Int = 300,
                repeatCount: Int = 0,
                techniques: Techniques = Techniques.FadeIn,
                delayInMls: Int = 0,
                callbackAnimation: CallbackAnimation? = null
        ) {
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

        fun playRotate(
                view: View? = null,
                animationListener: Animation.AnimationListener? = null
        ) {
            view?.let {
                val anim = RotateAnimation(0.0f, 90.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f)
                anim.interpolator = LinearInterpolator()
                anim.fillAfter = true
                //anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
                anim.duration = 300L //Put desired duration per anim cycle here, in milliseconds
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

        fun playAnimRandomDuration(
                viewToAnimate: View,
                duration: Long = Random.nextLong(501)
        ) {
            val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = duration //to make duration random number between [0,500)
//            anim.duration = Random.nextLong(501) //to make duration random number between [0,500)
//            anim.duration = 500 //to make duration random number between [0,500)
            viewToAnimate.startAnimation(anim)
        }
    }
}
