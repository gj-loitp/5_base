package com.core.utilities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.R
import com.views.LToast

object LPickerUtil {
    fun cropImage(activity: Activity, picUri: Uri, CROP_PIC_REQUEST_CODE: Int,
                  aspectX: Int, aspectY: Int, outputX: Int, outputY: Int) {
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
        } catch (anfe: ActivityNotFoundException) {
            val errorMessage = "Whoops - your device doesn't support the crop action!"
            LToast.showShort(activity, errorMessage, R.drawable.l_bkg_horizontal)
        }
        // respond to users whose devices do not support the crop action
    }
}
