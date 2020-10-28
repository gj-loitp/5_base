package com.core.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.core.common.Constants
import com.utils.util.FileUtils
import com.views.imageview.pinchtozoom.ImageMatrixTouchHandler
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File
import java.util.*
import kotlin.math.min

//https://github.com/wasabeef/glide-transformations
class LImageUtil {
    companion object {
        private val logTag = "LImageUtil"

        val randomUrlFlickr: String
            get() {
                val r = LStoreUtil.getRandomNumber(Constants.ARR_URL_BKG_FLICKR.size)
                return Constants.ARR_URL_BKG_FLICKR[r]
            }

        //for flick api url_m -> url_b
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
            linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
            if (linkUrlM.contains(".jpg")) {
                linkUrlM = linkUrlM.replace(".jpg", "_t.jpg")
            } else if (linkUrlM.contains(".png")) {
                linkUrlM = linkUrlM.replace(".png", "_t.png")
            }
            return linkUrlM
        }

        //for flick api url_m -> url_b
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
            linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
            if (linkUrlM.contains(".jpg")) {
                linkUrlM = linkUrlM.replace(".jpg", "_z.jpg")
            } else if (linkUrlM.contains(".png")) {
                linkUrlM = linkUrlM.replace(".png", "_z.png")
            }
            return linkUrlM
        }

        //for flick api url_m -> url_n
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
            linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
            if (linkUrlM.contains(".jpg")) {
                linkUrlM = linkUrlM.replace(".jpg", "_n.jpg")
            } else if (linkUrlM.contains(".png")) {
                linkUrlM = linkUrlM.replace(".png", "_n.png")
            }
            return linkUrlM
        }

        //for flick api url_m -> url_b
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
            linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
            if (linkUrlM.contains(".jpg")) {
                linkUrlM = linkUrlM.replace(".jpg", "_b.jpg")
            } else if (linkUrlM.contains(".png")) {
                linkUrlM = linkUrlM.replace(".png", "_b.png")
            }
            return linkUrlM
        }

        @SuppressLint("ClickableViewAccessibility")
        fun setImageViewZoom(iv: ImageView?) {
            iv?.setOnTouchListener(ImageMatrixTouchHandler(iv.context))
        }

        fun resizeImage(context: Context?, file: File?, scaleTo: Int = 1024, folderPath: String?): File? {
            if (context == null || file == null || folderPath.isNullOrEmpty()) {
                return null
            }
            if (!file.exists()) {
                return null
            }
            try {
                val srcFilePath = file.path
                val destFilePath = LStoreUtil.getFolderPath(folderName = folderPath) + "/" + file.name
                val resultCopy = FileUtils.copyFile(srcFilePath, destFilePath)
                LLog.d(logTag, "resultCopy $resultCopy")

                val copiedFile = File(destFilePath)
                if (!copiedFile.exists()) {
                    return copiedFile
                }

                val ei = ExifInterface(copiedFile.path)
                val o = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

                val bmOptions = BitmapFactory.Options()
                bmOptions.inJustDecodeBounds = true
                BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions)

                val photoW = bmOptions.outWidth
                val photoH = bmOptions.outHeight

                // Determine how much to scale down the image
                val scaleFactor = min(a = photoW / scaleTo, b = photoH / scaleTo)

                bmOptions.inJustDecodeBounds = false
                bmOptions.inSampleSize = scaleFactor

                val resized = BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions)
                        ?: return null
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

        //for glide
        fun clear(context: Context?, target: View?) {
            if (context == null || target == null) {
                return
            }
            Glide.with(context).clear(target)
        }

//        fun load(
//                context: Context?,
//                drawableRes: Int,
//                imageView: ImageView?
//        ) {
//            if (context == null || imageView == null) {
//                return
//            }
//            Glide.with(context).load(drawableRes).into(imageView)
//        }
//
//        fun load(
//                context: Context?,
//                imageFile: File?,
//                imageView: ImageView?
//        ) {
//            if (context == null || imageFile == null || imageView == null) {
//                return
//            }
//            Glide.with(context).load(imageFile).into(imageView)
//        }
//
//        fun load(
//                context: Context?,
//                uri: Uri?,
//                imageView: ImageView?
//        ) {
//            if (context == null || uri == null || imageView == null) {
//                return
//            }
//            Glide.with(context).load(uri).into(imageView)
//        }

        //any maybe url: String, drawableRes: Int, imageFile: File?, uri: Uri?,
        fun load(context: Context?,
                 any: Any?,
                 imageView: ImageView?,
                 resPlaceHolder: Int = R.color.colorPrimary,
                 resError: Int = R.color.red,
                 drawableRequestListener: RequestListener<Drawable>? = null) {

            if (context == null || any == null || imageView == null) {
                return
            }
            Glide.with(context)
                    .load(any)
                    //.transition(DrawableTransitionOptions.withCrossFade())//wont work with de.hdodenhof.circleimageview.CircleImageView
                    .apply(
                            RequestOptions()
                                    .placeholder(resPlaceHolder)
                                    .error(resError)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .listener(drawableRequestListener)
                    .into(imageView)
        }

        fun loadRound(
                url: String?,
                imageView: ImageView?,
                roundingRadius: Int = 45,
                resPlaceHolder: Int = R.color.colorPrimary,
                requestListener: RequestListener<Drawable>? = null
        ) {
            if (url.isNullOrEmpty() || imageView == null) {
                return
            }
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(
                    RoundedCornersTransformation(roundingRadius, 0, RoundedCornersTransformation.CornerType.BOTTOM)
            ).placeholder(resPlaceHolder)
            Glide.with(imageView.context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(imageView)
        }

        fun loadCircle(
                url: String?,
                imageView: ImageView?,
                resPlaceHolder: Int = R.color.colorPrimary,
                resError: Int = R.color.red,
                requestListener: RequestListener<Drawable>? = null
        ) {
            if (url.isNullOrEmpty() || imageView == null) {
                return
            }
            Glide.with(imageView.context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(
                            RequestOptions
                                    .circleCropTransform()
                                    .placeholder(resPlaceHolder)
                                    .error(resError)
                    )
                    .listener(requestListener)
                    .into(imageView)
        }
    }
}
