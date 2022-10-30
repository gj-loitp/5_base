package com.loitpcore.views.piano.listener;

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public interface OnLoadAudioListener {

    void loadPianoAudioStart();

    void loadPianoAudioFinish();

    void loadPianoAudioError(Exception e);

    void loadPianoAudioProgress(int progress);
}
