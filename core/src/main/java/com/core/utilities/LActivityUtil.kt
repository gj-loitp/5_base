package com.core.utilities

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import android.view.WindowManager
import com.R
import com.core.common.Constants
import com.data.ActivityData

/**
 * Created by www.muathu@gmail.com on 1/3/2018.
 */

class LActivityUtil {

    companion object {
        // This snippet hides the system bars.
        @JvmStatic
        fun hideSystemUI(mDecorView: View) {
            // Set the IMMERSIVE flag.
            // Set the content to appear under the system bars so that the content
            // doesn't resize when the system bars hide and show.
            mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }

        // This snippet shows the system bars. It does this by removing all the flags
        // except for the ones that make the content appear under the system bars.
        @JvmStatic
        fun showSystemUI(mDecorView: View) {
            mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }

        @JvmStatic
        fun tranIn(context: Context?) {
            if (context == null) {
                return
            }
            when (ActivityData.instance.type) {
                Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
                    transActivityNoAniamtion(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
                    //do nothing
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT -> {
                    slideLeft(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT -> {
                    slideRight(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN -> {
                    slideDown(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP -> {
                    slideUp(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
                    fade(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
                    zoom(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
                    windmill(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
                    diagonal(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
                    spin(context as Activity)
                }
            }
        }

        @JvmStatic
        fun tranOut(context: Context) {
            when (ActivityData.instance.type) {
                Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
                    transActivityNoAniamtion(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
                    //do nothing
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT -> {
                    slideRight(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT -> {
                    slideLeft(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN -> {
                    slideUp(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP -> {
                    slideDown(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
                    fade(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
                    zoom(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
                    windmill(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
                    diagonal(context as Activity)
                }
                Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
                    spin(context as Activity)
                }
            }
        }

        @JvmStatic
        fun transActivityNoAniamtion(context: Context) {
            (context as Activity).overridePendingTransition(0, 0)
        }

        @JvmStatic
        fun slideLeft(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_slide_left_enter, R.anim.l_slide_left_exit)
        }

        @JvmStatic
        fun slideRight(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_slide_in_left, R.anim.l_slide_out_right)
        }

        @JvmStatic
        fun slideDown(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_slide_down_enter, R.anim.l_slide_down_exit)
        }

        @JvmStatic
        fun slideUp(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_slide_up_enter, R.anim.l_slide_up_exit)
        }

        @JvmStatic
        fun zoom(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_zoom_enter, R.anim.l_zoom_exit)
        }

        @JvmStatic
        fun fade(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_fade_enter, R.anim.l_fade_exit)
        }

        @JvmStatic
        fun windmill(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_windmill_enter, R.anim.l_windmill_exit)
        }

        @JvmStatic
        fun spin(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_spin_enter, R.anim.l_spin_exit)
        }

        @JvmStatic
        fun diagonal(context: Context) {
            (context as Activity).overridePendingTransition(R.anim.l_diagonal_right_enter, R.anim.l_diagonal_right_exit)
        }

        @JvmStatic
        fun toggleFullScreen(activity: Activity) {
            val attrs = activity.window.attributes
            attrs.flags = attrs.flags xor WindowManager.LayoutParams.FLAG_FULLSCREEN
            activity.window.attributes = attrs
        }

        @JvmStatic
        fun toggleScreenOritation(activity: Activity) {
            val s = getScreenOrientation(activity)
            if (s == Configuration.ORIENTATION_LANDSCAPE) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            } else if (s == Configuration.ORIENTATION_PORTRAIT) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }

        @JvmStatic
        fun changeScreenPortrait(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        @JvmStatic
        fun changeScreenLandscape(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        @JvmStatic
        fun getScreenOrientation(activity: Activity): Int {
            return activity.resources.configuration.orientation
        }
    }

}
