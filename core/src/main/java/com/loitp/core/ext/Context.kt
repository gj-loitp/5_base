package com.loitp.core.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.data.ActivityData

//check xem app hien tai co phai la default launcher hay khong
fun Context.isDefaultLauncher(): Boolean {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    val resolveInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.resolveActivity(
            intent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        @Suppress("DEPRECATION")
        packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    }

    val currentLauncherName = resolveInfo?.activityInfo?.packageName
    if (currentLauncherName == packageName) {
        return true
    }
    return false
}

//mo app setting default cua device
fun Context.launchSystemSetting(
    packageName: String
) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    this.startActivity(intent)
    tranIn()
}

fun Context.tranIn() {
    if (this !is Activity) {
        return
    }
    when (ActivityData.instance.type) {
        Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
            transActivityNoAnimation()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
            // do nothing
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
            slideLeft()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
            slideRight()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
            slideDown()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
            slideUp()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
            fade()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
            zoom()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
            windmill()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
            diagonal()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
            spin()
        }
    }
}

fun Context.tranOut() {
    if (this !is Activity) {
        return
    }
    when (ActivityData.instance.type) {
        Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
            transActivityNoAnimation()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
            // do nothing
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
            slideRight()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
            slideLeft()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
            slideUp()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
            slideDown()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
            fade()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
            zoom()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
            windmill()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
            diagonal()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
            spin()
        }
    }
}

fun Context.transActivityNoAnimation() {
    if (this is Activity) {
        this.overridePendingTransition(0, 0)
    }
}

fun Context.slideLeft() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_slide_left_enter,
            R.anim.l_slide_left_exit
        )
    }
}

fun Context.slideRight() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_slide_in_left, R.anim.l_slide_out_right)
    }
}

fun Context.slideDown() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_slide_down_enter,
            R.anim.l_slide_down_exit
        )
    }
}

fun Context.slideUp() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_slide_up_enter, R.anim.l_slide_up_exit)
    }
}

fun Context.zoom() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_zoom_enter, R.anim.l_zoom_exit)
    }
}

fun Context.fade() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_fade_enter, R.anim.l_fade_exit)
    }
}

fun Context.windmill() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_windmill_enter, R.anim.l_windmill_exit)
    }
}

fun Context.spin() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_spin_enter, R.anim.l_spin_exit)
    }
}

fun Context.diagonal() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_diagonal_right_enter,
            R.anim.l_diagonal_right_exit
        )
    }
}
