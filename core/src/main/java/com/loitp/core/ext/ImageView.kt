package com.loitp.core.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utils.FileUtils
import com.ortiz.touchview.TouchImageView
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.math.min

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun randomUrlFlickr(): String {
    val r = getRandomNumber(Constants.ARR_URL_BKG_FLICKR.size)
    return Constants.ARR_URL_BKG_FLICKR[r]
}

// for flick api url_m -> url_b
fun getFlickrLink100(urlM: String): String {
    var linkUrlM = urlM
    /*
    s	small square 75x75
    q	large square 150x150
    t	thumbnail, 100 on longest side
    m	small, 240 on longest side
    n	small, 320 on longest side
            -	medium, 500 on longest side
    z	medium 640, 640 on longest side
    c	medium 800, 800 on longest side†
    b	large, 1024 on longest side*
            h	large 1600, 1600 on longest side†
    k	large 2048, 2048 on longest side†
    o	original image, either a jpg, gif or png, depending on source format
    */

    if (linkUrlM.isEmpty()) {
        return ""
    }
    linkUrlM = linkUrlM.lowercase(Locale.getDefault())
    if (linkUrlM.contains(".jpg")) {
        linkUrlM = linkUrlM.replace(".jpg", "_t.jpg")
    } else if (linkUrlM.contains(".png")) {
        linkUrlM = linkUrlM.replace(".png", "_t.png")
    }
    return linkUrlM
}

// for flick api url_m -> url_b
fun getFlickrLink640(urlM: String): String {
    var linkUrlM = urlM
    /*
    s	small square 75x75
    q	large square 150x150
    t	thumbnail, 100 on longest side
    m	small, 240 on longest side
    n	small, 320 on longest side
            -	medium, 500 on longest side
    z	medium 640, 640 on longest side
    c	medium 800, 800 on longest side†
    b	large, 1024 on longest side*
            h	large 1600, 1600 on longest side†
    k	large 2048, 2048 on longest side†
    o	original image, either a jpg, gif or png, depending on source format
    */

    if (linkUrlM.isEmpty()) {
        return ""
    }
    linkUrlM = linkUrlM.lowercase(Locale.getDefault())
    if (linkUrlM.contains(".jpg")) {
        linkUrlM = linkUrlM.replace(".jpg", "_z.jpg")
    } else if (linkUrlM.contains(".png")) {
        linkUrlM = linkUrlM.replace(".png", "_z.png")
    }
    return linkUrlM
}

// for flick api url_m -> url_n
fun getFlickrLink320(urlM: String): String {
    var linkUrlM = urlM
    /*
    s	small square 75x75
    q	large square 150x150
    t	thumbnail, 100 on longest side
    m	small, 240 on longest side
    n	small, 320 on longest side
            -	medium, 500 on longest side
    z	medium 640, 640 on longest side
    c	medium 800, 800 on longest side†
    b	large, 1024 on longest side*
            h	large 1600, 1600 on longest side†
    k	large 2048, 2048 on longest side†
    o	original image, either a jpg, gif or png, depending on source format
    */

    if (linkUrlM.isEmpty()) {
        return ""
    }
    linkUrlM = linkUrlM.lowercase(Locale.getDefault())
    if (linkUrlM.contains(".jpg")) {
        linkUrlM = linkUrlM.replace(".jpg", "_n.jpg")
    } else if (linkUrlM.contains(".png")) {
        linkUrlM = linkUrlM.replace(".png", "_n.png")
    }
    return linkUrlM
}

// for flick api url_m -> url_b
fun getFlickrLink1024(urlM: String): String {
    var linkUrlM = urlM
    /*
    s	small square 75x75
    q	large square 150x150
    t	thumbnail, 100 on longest side
    m	small, 240 on longest side
    n	small, 320 on longest side
            -	medium, 500 on longest side
    z	medium 640, 640 on longest side
    c	medium 800, 800 on longest side†
    b	large, 1024 on longest side*
            h	large 1600, 1600 on longest side†
    k	large 2048, 2048 on longest side†
    o	original image, either a jpg, gif or png, depending on source format
    */

    if (linkUrlM.isEmpty()) {
        return ""
    }
    linkUrlM = linkUrlM.lowercase(Locale.getDefault())
    if (linkUrlM.contains(".jpg")) {
        linkUrlM = linkUrlM.replace(".jpg", "_b.jpg")
    } else if (linkUrlM.contains(".png")) {
        linkUrlM = linkUrlM.replace(".png", "_b.png")
    }
    return linkUrlM
}

@Suppress("unused")
fun Context?.resizeImage(
    file: File?, scaleTo: Int = 1024, folderPath: String?
): File? {
    if (this == null || file == null || folderPath.isNullOrEmpty()) {
        return null
    }
    if (!file.exists()) {
        return null
    }
    try {
        val srcFilePath = file.path
        val destFilePath = getFolderPath(folderName = folderPath) + "/" + file.name
        val resultCopy = FileUtils.copyFile(srcFilePath, destFilePath)
        print("resultCopy $resultCopy")

        val copiedFile = File(destFilePath)
        if (!copiedFile.exists()) {
            return copiedFile
        }

        val ei = ExifInterface(copiedFile.path)
        val o = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions)

        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = min(a = photoW / scaleTo, b = photoH / scaleTo)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions) ?: return null
        copiedFile.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)

            val ei2 = ExifInterface(copiedFile.path)
            ei2.setAttribute(ExifInterface.TAG_ORIENTATION, o.toString())
            ei2.saveAttributes()

            resized.recycle()
        }
        return copiedFile
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

// for glide
fun View?.clear(
) {
    if (this == null) {
        return
    }
    Glide.with(this.context).clear(this)
}

// any maybe url: String, drawableRes: Int, imageFile: File?, uri: Uri?,
fun ImageView?.loadGlide(
    any: Any?,
    resPlaceHolder: Int = R.color.transparent,
    resError: Int = R.color.red,
    transformation: Transformation<Bitmap>? = null,
    drawableRequestListener: RequestListener<Drawable>? = null
) {

    if (this == null || any == null) {
        return
    }
    val requestOptions = if (transformation == null) {
        RequestOptions().placeholder(resPlaceHolder).error(resError)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    } else {
        RequestOptions().placeholder(resPlaceHolder).error(resError).transform(transformation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }
    Glide.with(this.context).load(any)
        // .transition(DrawableTransitionOptions.withCrossFade())//wont work with de.hdodenhof.circleimageview.CircleImageView
        .apply(requestOptions).listener(drawableRequestListener).into(this)
}

@Suppress("unused")
fun ImageView.loadHighQuality(
    any: Any?,
    resPlaceHolder: Int = R.color.transparent,
    resError: Int = R.color.red,
    drawableRequestListener: RequestListener<Drawable>? = null
) {
    Glide.with(this).load(any).apply(
        RequestOptions().placeholder(resPlaceHolder).error(resError).fitCenter()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
    ).listener(drawableRequestListener).into(this)
}

fun TouchImageView.setZoomFitWidthScreen() {
    this.post {
        val maxZoomRatio =
            LScreenUtil.screenWidth.toFloat() / this.getWidthOfView().toFloat()
        this.setMaxZoomRatio(maxZoomRatio)
    }
}

fun ImageView?.setImageFromAsset(
    fileName: String,
) {
    this?.let { iv ->
        run {
            val drawable: Drawable?
            var stream: InputStream? = null
            try {
                stream = LAppResource.application.assets.open("img/$fileName")
                drawable = Drawable.createFromStream(stream, null)
                drawable?.let {
                    iv.setImageDrawable(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    stream?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
