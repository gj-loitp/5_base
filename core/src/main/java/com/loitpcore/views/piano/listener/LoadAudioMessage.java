package com.loitpcore.views.piano.listener;

/**
 * Created by Loitp on 27.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public interface LoadAudioMessage {
    void sendStartMessage();

    void sendFinishMessage();

    void sendErrorMessage(Exception e);

    void sendProgressMessage(int progress);
}
