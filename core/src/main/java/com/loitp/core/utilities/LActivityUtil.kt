package com.loitp.core.utilities

import android.app.Activity
import android.content.Context
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.data.ActivityData

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LActivityUtil {

    companion object {

        @JvmStatic
        fun tranIn(context: Context?) {
            if (context == null || context !is Activity) {
                return
            }
            when (ActivityData.instance.type) {
                Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
                    transActivityNoAnimation(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
                    // do nothing
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
                    slideLeft(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
                    slideRight(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
                    slideDown(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
                    slideUp(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
                    fade(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
                    zoom(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
                    windmill(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
                    diagonal(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
                    spin(context)
                }
            }
        }

        @JvmStatic
        fun tranOut(context: Context) {
            if (context !is Activity) {
                return
            }
            when (ActivityData.instance.type) {
                Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
                    transActivityNoAnimation(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
                    // do nothing
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
                    slideRight(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
                    slideLeft(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
                    slideUp(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
                    slideDown(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
                    fade(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
                    zoom(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
                    windmill(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
                    diagonal(context)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
                    spin(context)
                }
            }
        }

        @JvmStatic
        fun transActivityNoAnimation(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }

        @JvmStatic
        fun slideLeft(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.l_slide_left_enter,
                    R.anim.l_slide_left_exit
                )
            }
        }

        @JvmStatic
        fun slideRight(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_slide_in_left, R.anim.l_slide_out_right)
            }
        }

        @JvmStatic
        fun slideDown(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.l_slide_down_enter,
                    R.anim.l_slide_down_exit
                )
            }
        }

        @JvmStatic
        fun slideUp(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_slide_up_enter, R.anim.l_slide_up_exit)
            }
        }

        @JvmStatic
        fun zoom(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_zoom_enter, R.anim.l_zoom_exit)
            }
        }

        @JvmStatic
        fun fade(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_fade_enter, R.anim.l_fade_exit)
            }
        }

        @JvmStatic
        fun windmill(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_windmill_enter, R.anim.l_windmill_exit)
            }
        }

        @JvmStatic
        fun spin(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(R.anim.l_spin_enter, R.anim.l_spin_exit)
            }
        }

        @JvmStatic
        fun diagonal(context: Context) {
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.l_diagonal_right_enter,
                    R.anim.l_diagonal_right_exit
                )
            }
        }

    }
}
