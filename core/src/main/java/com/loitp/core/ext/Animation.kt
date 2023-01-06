package com.loitp.core.ext

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.*
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.loitp.R
import kotlin.random.Random

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun View.play(
    duration: Int = 300,
    repeatCount: Int = 0,
    techniques: Techniques = Techniques.FadeIn,
    delayInMls: Int = 0,
    repeatMode: Int = ValueAnimator.REVERSE,
    onCancel: ((Unit) -> Unit)? = null,
    onEnd: ((Unit) -> Unit)? = null,
    onRepeat: ((Unit) -> Unit)? = null,
    onStart: ((Unit) -> Unit)? = null
) {
    this.clearAnimation()
    YoYo.with(techniques)
        .duration(duration.toLong())
        .repeat(repeatCount)
        .repeatMode(repeatMode)
        .onCancel {
            onCancel?.invoke(Unit)
        }
        .onEnd {
            onEnd?.invoke(Unit)
        }
        .onRepeat {
            onRepeat?.invoke(Unit)
        }
        .onStart {
            onStart?.invoke(Unit)
        }
        .delay(delayInMls.toLong())
        .playOn(this)
}

fun View.playRotate(
    animationListener: Animation.AnimationListener? = null
) {
    val anim = RotateAnimation(
        0.0f, 90.0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    anim.interpolator = LinearInterpolator()
    anim.fillAfter = true
    // anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
    anim.duration = 300L // Put desired duration per anim cycle here, in milliseconds
    anim.setAnimationListener(animationListener)
    this.startAnimation(anim)
}

fun View.slideInDown() {
    val slideDown = AnimationUtils.loadAnimation(this.context, R.anim.l_slide_down)
    this.startAnimation(slideDown)
}

fun View.slideInUp() {
    val slideDown = AnimationUtils.loadAnimation(this.context, R.anim.l_slide_up)
    this.startAnimation(slideDown)
}

fun View.playAnimRandomDuration(
    duration: Long = Random.nextLong(501)
) {
    val anim = ScaleAnimation(
        0.0f,
        1.0f,
        0.0f,
        1.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    anim.duration = duration // to make duration random number between [0,500)
//            anim.duration = Random.nextLong(501) //to make duration random number between [0,500)
//            anim.duration = 500 //to make duration random number between [0,500)
    this.startAnimation(anim)
}
