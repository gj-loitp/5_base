package vn.loitp.app.activity.customviews.videoview.uizavideo.listerner;

import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer;

import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/12/2018.
 */

public class VideoAdPlayerListerner implements VideoAdPlayer.VideoAdPlayerCallback {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = VideoAdPlayerListerner.class.getSimpleName();

    @Override
    public void onPlay() {
        LLog.d(TAG, "onPlay");
    }

    @Override
    public void onVolumeChanged(int i) {
        LLog.d(TAG, "onVolumeChanged");
    }

    @Override
    public void onPause() {
        LLog.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        LLog.d(TAG, "onResume");
    }

    @Override
    public void onEnded() {
        LLog.d(TAG, "onEnded");
    }

    @Override
    public void onError() {
        LLog.d(TAG, "onError");
    }
}
