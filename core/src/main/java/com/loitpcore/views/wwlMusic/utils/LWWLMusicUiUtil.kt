package com.loitpcore.views.wwlMusic.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.loitpcore.R
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LWWLMusicUiUtil {

    @JvmStatic
    fun getGridColumnCount(res: Resources): Int {
        return min(
            getGridColumnContentWidth(res) / res.getDimensionPixelSize(R.dimen.column_min_size),
            5
        )
    }

    private fun getGridColumnContentWidth(res: Resources): Int {
        return res.displayMetrics.widthPixels - 2 * res.getDimensionPixelSize(R.dimen.card_spacing)
    }

    @JvmStatic
    fun hideSystemUI(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (!ViewConfiguration.get(activity).hasPermanentMenuKey()) {
            var newUiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            activity.window.decorView.systemUiVisibility = newUiOptions
        }
    }

    @JvmStatic
    fun showSystemUI(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (!ViewConfiguration.get(activity).hasPermanentMenuKey()) {
            val newUiOptions = View.VISIBLE
            activity.window.decorView.systemUiVisibility = newUiOptions
        }
    }

    @JvmStatic
    fun updateStatusBarAlpha(activity: Activity, alpha: Float) {
        val color = ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        val color2 = Color.BLACK
        val color3 =
            LWWLMusicViewHelper.evaluateColorAlpha(max(0.0f, min(1.0f, alpha)), color, color2)
        activity.window.statusBarColor = color3
    }
}
