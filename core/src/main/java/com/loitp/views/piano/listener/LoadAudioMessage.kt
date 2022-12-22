package com.loitp.views.piano.listener

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface LoadAudioMessage {
    fun sendStartMessage()
    fun sendFinishMessage()
    fun sendErrorMessage(e: Exception?)
    fun sendProgressMessage(progress: Int)
}