package com.core.utilities

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException

/**
 * Created by www.muathu@gmail.com on 6/1/2017.
 */

object LSoundUtil {
    fun startMusicFromAsset(context: Context, fileName: String) {
        val mediaPlayer = MediaPlayer()
        try {
            val assetFileDescriptor = context.assets.openFd(fileName)
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
                    //LLog.d(TAG, "onCompletion >>> release");
                    it.reset()
                    it.release()
                    mPlayer = null
                }
            }
        } catch (e: IOException) {
            //LLog.d(TAG, "startMusicFromAsset: " + e.toString());
        }

    }
}
