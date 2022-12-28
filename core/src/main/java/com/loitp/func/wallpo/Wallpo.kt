package com.loitp.func.wallpo

import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import java.io.IOException

/**
 * Created by Loitp on 28,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object Wallpo {
    fun setMainScreenWallpaper(
        context: Context,
        imageView: ImageView,
    ): Boolean {
        val manager = WallpaperManager.getInstance(context)
        try {
            val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
            manager.setBitmap(
                /* fullImage = */ bitmap,
                /* visibleCropHint = */null,
                /* allowBackup = */true,
                /* which = */WallpaperManager.FLAG_SYSTEM
            )
            return true
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    fun setLockScreenWallpaper(
        activity: Activity?,
        imageView: ImageView,
    ): Boolean {
        val manager = WallpaperManager.getInstance(activity)
        try {
            manager.setBitmap(
                /* fullImage = */ (imageView.drawable as BitmapDrawable).bitmap,
                /* visibleCropHint = */ null,
                /* allowBackup = */ true,
                /* which = */ WallpaperManager.FLAG_LOCK
            )
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
}
