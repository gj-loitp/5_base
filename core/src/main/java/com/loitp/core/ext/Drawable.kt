package com.loitp.core.ext

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun createGradientDrawableWithRandomColor(): GradientDrawable {
    val color = getRandomColor()
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(color)
    gradientDrawable.cornerRadius = 0f
    gradientDrawable.setStroke(1, color)
    return gradientDrawable
}


fun createGradientDrawableWithColor(
    colorMain: Int,
    colorStroke: Int
): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(colorMain)
    gradientDrawable.cornerRadius = 90f
    gradientDrawable.setStroke(3, colorStroke)
    return gradientDrawable
}

fun Bitmap.bitmapToFile(
    fileNameToSave: String
): File? { // File name like "image.png"
    //create a file to write bitmap data
    var file: File? = null
    return try {
        file = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + fileNameToSave
        )
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        this.compress(
            /* format = */ Bitmap.CompressFormat.PNG,
            /* quality = */ 0,
            /* stream = */ bos
        ) // YOU can also save it in JPEG
        val bitmapData = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        file // it will return null
    }
}
