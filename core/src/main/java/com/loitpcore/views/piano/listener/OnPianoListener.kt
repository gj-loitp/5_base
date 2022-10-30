package com.loitpcore.views.piano.listener

import com.loitpcore.views.piano.entity.Piano.PianoKeyType
import com.loitpcore.views.piano.entity.Piano.PianoVoice

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface OnPianoListener {
    fun onPianoInitFinish()
    fun onPianoClick(type: PianoKeyType?, voice: PianoVoice?, group: Int, positionOfGroup: Int)
}