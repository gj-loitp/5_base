package com.core.utilities

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.R
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

/**
 * Created by www.muathu@gmail.com on 6/9/2017.
 */

class LAnimationUtil {
    interface Callback {
        fun onCancel()

        fun onEnd()

        fun onRepeat()

        fun onStart()
    }

    companion object {
        fun play(view: View?, duration: Int, repeatCount: Int, techniques: Techniques, delayInMls: Int, callback: Callback?) {
            if (view == null) {
                return
            }

            view.clearAnimation()
            YoYo.with(techniques)
                    .duration(duration.toLong())
                    .repeat(repeatCount)
                    .onCancel {
                        callback?.onCancel()
                    }
                    .onEnd {
                        callback?.onEnd()
                    }
                    .onRepeat {
                        callback?.onRepeat()
                    }
                    .onStart {
                        callback?.onStart()
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

        fun play(view: View?, techniques: Techniques, callback: Callback) {
            play(view, 500, 1, techniques, 0, callback)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int) {
            play(view, duration, 1, techniques, 0, null)
        }

        fun playDuration(view: View?, techniques: Techniques, duration: Int, callback: Callback) {
            play(view, duration, 1, techniques, 0, callback)
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
    }
}
