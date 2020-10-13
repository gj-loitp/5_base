package com.core.utilities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.R
import com.core.common.Constants
import com.function.epub.core.EpubReaderReadActivity
import com.function.epub.model.BookInfo
import com.function.epub.model.BookInfoData

class LReaderUtil {

    companion object {
        val defaultCover: Int
            get() = R.drawable.l_df_cover_epub

        fun decodeBitmapFromByteArray(coverImage: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size, options)
        }

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

        fun readEpub(activity: Activity?, bookInfo: BookInfo?, admobAdIdBanner: String) {
            if (activity == null || bookInfo == null) {
                throw NullPointerException("activity == null || bookInfo == null")
            }
            val intent = Intent(activity, EpubReaderReadActivity::class.java)
            BookInfoData.instance.bookInfo = bookInfo
            intent.putExtra(Constants.AD_UNIT_ID_BANNER, admobAdIdBanner)
            activity.startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
