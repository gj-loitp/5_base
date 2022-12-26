package com.loitp.views.wwl.music.utils

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LWWLMusicViewHelper {

    @JvmStatic
    fun alpha255(f: Float): Int {
        if (f <= 0.0f) {
            return 0
        }
        return if (f >= 1.0f) {
            255
        } else (255.0f * f).toInt()
    }

    fun evaluateColorAlpha(
        f: Float,
        color1: Int,
        color2: Int
    ): Int {
        val c14 = color1 ushr 24
        val c13 = color1 shr 16 and 255
        val c12 = color1 shr 8 and 255
        val c11 = color1 and 255
        val c24 = color2 ushr 24
        val c23 = color2 shr 16 and 255
        val c22 = color2 shr 8 and 255
        val c21 = color2 and 255
        return c11 + ((c21 - c11).toFloat() * f).toInt() or (c14 + ((c24 - c14).toFloat() * f).toInt() shl 24 or (c13 + ((c23 - c13).toFloat() * f).toInt() shl 16) or (((c22 - c12).toFloat() * f).toInt() + c12 shl 8))
    }
}
