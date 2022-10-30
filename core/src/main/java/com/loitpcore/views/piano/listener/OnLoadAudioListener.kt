package com.loitpcore.views.piano.listener

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface OnLoadAudioListener {
    fun loadPianoAudioStart()
    fun loadPianoAudioFinish()
    fun loadPianoAudioError(e: Exception?)
    fun loadPianoAudioProgress(progress: Int)
}