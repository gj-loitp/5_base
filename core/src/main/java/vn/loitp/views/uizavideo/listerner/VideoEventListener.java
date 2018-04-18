package vn.loitp.views.uizavideo.listerner;

import android.view.Surface;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class VideoEventListener implements VideoRendererEventListener {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = Constants.LOITP;

    @Override
    public void onVideoEnabled(DecoderCounters counters) {
        LLog.d(TAG, "onVideoEnabled");
    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        LLog.d(TAG, "onVideoDecoderInitialized decoderName: " + decoderName + ", initializedTimestampMs " + initializedTimestampMs + ", initializationDurationMs " + initializationDurationMs);
    }

    @Override
    public void onVideoInputFormatChanged(Format format) {
        LLog.d(TAG, "onVideoInputFormatChanged");
    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {
        LLog.d(TAG, "onDroppedFrames count " + count + ",elapsedMs " + elapsedMs);
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        LLog.d(TAG, "onVideoSizeChanged " + width + "x" + height + ", pixelWidthHeightRatio " + pixelWidthHeightRatio);
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {
        LLog.d(TAG, "onRenderedFirstFrame");
    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {
        LLog.d(TAG, "onVideoDisabled");
    }
}