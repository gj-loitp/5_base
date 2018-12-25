package vn.loitp.views.exo;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;

public interface Callback {
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState);
    public void onPlayerError(ExoPlaybackException error);
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters);
    public void onVideoSizeChanged(int width, int height);
}
