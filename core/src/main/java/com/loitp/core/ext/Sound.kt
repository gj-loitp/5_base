package com.loitp.core.ext

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun Context.startMusicFromAsset(fileName: String) {
    val mediaPlayer = MediaPlayer()
    try {
        val assetFileDescriptor = this.assets.openFd(fileName)
        mediaPlayer.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        assetFileDescriptor.close()
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { player ->
            var mPlayer = player
            mPlayer?.let {
                it.stop()
                it.reset()
                it.release()
                mPlayer = null
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
