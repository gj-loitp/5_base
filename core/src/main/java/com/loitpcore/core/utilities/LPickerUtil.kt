package com.loitpcore.core.utilities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Suppress("unused")
class LPickerUtil {

    companion object {
        fun cropImage(
            activity: Activity?,
            picUri: Uri?,
            CROP_PIC_REQUEST_CODE: Int,
            aspectX: Int,
            aspectY: Int,
            outputX: Int,
            outputY: Int,
            error: ((String) -> Unit)? = null
        ) {

            if (activity == null || picUri == null) {
                return
            }

            try {
                val cropIntent = Intent("com.android.camera.action.CROP")
                cropIntent.setDataAndType(picUri, "image/*")
                cropIntent.putExtra("crop", "true")
                cropIntent.putExtra("aspectX", aspectX)
                cropIntent.putExtra("aspectY", aspectY)
                cropIntent.putExtra("outputX", outputX)
                cropIntent.putExtra("outputY", outputY)
                cropIntent.putExtra("noFaceDetection", true)
                cropIntent.putExtra("return-data", true)
                activity.startActivityForResult(cropIntent, CROP_PIC_REQUEST_CODE)
            } catch (activityNotFoundException: ActivityNotFoundException) {
                activityNotFoundException.printStackTrace()
                val errorMessage = "Whoops - your device doesn't support the crop action!"
                error?.invoke(errorMessage)
            }
        }
    }
}
