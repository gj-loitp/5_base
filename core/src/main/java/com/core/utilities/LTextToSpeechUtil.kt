package com.core.utilities

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import java.util.*

/**
 * Created by Loitp on 5/6/2017.
 */

class LTextToSpeechUtil private constructor() : TextToSpeech.OnInitListener {
    private val TAG = javaClass.simpleName
    private var tts: TextToSpeech? = null
    private var context: Context? = null

    fun setupTTS(context: Context) {
        this.context = context
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LLog.d(TAG, "This Language is not supported")
            } else {
                //speakOut("Example");
            }
        } else {
            LLog.d("TTS", "Initilization Failed!")
        }
    }

    fun speakOut(text: String) {
        if (tts == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    fun destroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    companion object {
        val instance = LTextToSpeechUtil()
    }
}
