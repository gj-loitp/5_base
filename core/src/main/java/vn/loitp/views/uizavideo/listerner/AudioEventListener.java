package vn.loitp.views.uizavideo.listerner;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class AudioEventListener implements AudioRendererEventListener {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = Constants.LOITP;

    @Override
    public void onAudioEnabled(DecoderCounters counters) {
        LLog.d(TAG, "onAudioEnabled");
    }

    @Override
    public void onAudioSessionId(int audioSessionId) {
        LLog.d(TAG, "onAudioSessionId audioSessionId: " + audioSessionId);
    }

    @Override
    public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        LLog.d(TAG, "onAudioDecoderInitialized");
    }

    @Override
    public void onAudioInputFormatChanged(Format format) {
        LLog.d(TAG, "onAudioInputFormatChanged");
    }

    @Override
    public void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        LLog.d(TAG, "onAudioSinkUnderrun");
    }

    @Override
    public void onAudioDisabled(DecoderCounters counters) {
        LLog.d(TAG, "onAudioDisabled");
    }
}