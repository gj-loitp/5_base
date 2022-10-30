package com.loitpcore.views.piano.listener;

import com.loitpcore.views.piano.entity.Piano;

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public interface OnPianoListener {
    void onPianoInitFinish();

    void onPianoClick(Piano.PianoKeyType type, Piano.PianoVoice voice, int group, int positionOfGroup);
}
