package com.loitp.core.utilities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.loitp.R
import com.loitp.func.epub.core.EpubReaderReadActivityFont
import com.loitp.func.epub.model.BookInfo
import com.loitp.func.epub.model.BookInfoData

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LReaderUtil {

    companion object {
        val defaultCover: Int
            get() = R.drawable.l_df_cover_epub

        fun decodeBitmapFromByteArray(
            coverImage: ByteArray,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap {
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

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
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

        fun readEpub(
            activity: Activity?,
            bookInfo: BookInfo?
        ) {
            if (activity == null || bookInfo == null) {
                throw NullPointerException("activity == null || bookInfo == null")
            }
            val intent = Intent(activity, EpubReaderReadActivityFont::class.java)
            BookInfoData.instance.bookInfo = bookInfo
            activity.startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
