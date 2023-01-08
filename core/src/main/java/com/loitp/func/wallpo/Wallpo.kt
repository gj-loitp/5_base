package com.loitp.func.wallpo

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.toBitmap
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
        try {
            val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
            WallpaperManager.getInstance(context).setBitmap(
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

    fun setMainScreenWallpaper(
        context: Context,
        color: Int,
    ): Boolean {
        try {
            val cd = ColorDrawable(color)
            cd.setBounds(0, 0, screenWidth, screenHeight)
            val bitmap = cd.toBitmap()
            WallpaperManager.getInstance(context).setBitmap(
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
        context: Context?,
        imageView: ImageView,
    ): Boolean {
        try {
            WallpaperManager.getInstance(context).setBitmap(
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

    fun setLockScreenWallpaper(
        context: Context?,
        color: Int,
    ): Boolean {
        try {
            val cd = ColorDrawable(color)
            cd.setBounds(0, 0, screenWidth, screenHeight)
            val bitmap = cd.toBitmap()
            WallpaperManager.getInstance(context).setBitmap(
                /* fullImage = */ bitmap,
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
