package vn.loitp.views.uizavideo.listerner;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class PlayerEventListener implements Player.EventListener {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = Constants.LOITP;

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
        LLog.d(TAG, "onTimelineChanged");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        LLog.d(TAG, "onTracksChanged");
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        LLog.d(TAG, "onLoadingChanged isLoading " + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        LLog.d(TAG, "onPlayerStateChanged playWhenReady: " + playWhenReady);
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        LLog.d(TAG, "onRepeatModeChanged repeatMode: " + repeatMode);
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        LLog.d(TAG, "onShuffleModeEnabledChanged shuffleModeEnabled: " + shuffleModeEnabled);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        LLog.d(TAG, "onPlayerError " + error.toString());
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        LLog.d(TAG, "onPositionDiscontinuity");
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        LLog.d(TAG, "onPlaybackParametersChanged");
    }

    @Override
    public void onSeekProcessed() {
        LLog.d(TAG, "onSeekProcessed");
    }
}
