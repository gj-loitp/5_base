package vn.loitp.core.loitp.uiza;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import vn.loitp.utils.util.AppUtils;

/**
 * Manages the {@link ExoPlayer}, the IMA plugin and all video playback.
 */
public final class FUZPlayerManager {
    private final String TAG = getClass().getSimpleName();
    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public FUZPlayerManager(Context context) {
        dataSourceFactory = new DefaultDataSourceFactory(context, AppUtils.getAppName());
    }

    public void init(Context context, final PlayerView playerView, String linkPlay, long contentPosition) {
        if (context == null || playerView == null || linkPlay == null || linkPlay.isEmpty()) {
            return;
        }
        // Create a default track selector.
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        playerView.setPlayer(player);

        // This is the MediaSource representing the content media (i.e. not the ad).
        //String contentUrl = context.getString(R.string.content_url);
        MediaSource contentMediaSource = buildMediaSource(Uri.parse(linkPlay));
        player.prepare(contentMediaSource);
        player.setPlayWhenReady(true);
        player.seekTo(contentPosition);
        //LLog.d(TAG, "seekTo contentPosition: " + contentPosition);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                //LLog.d(TAG, "onLoadingChanged " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (callback != null) {
                    callback.onPlayerStateChanged(playWhenReady, playbackState);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
        });
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                //LLog.d(TAG, "onVideoSizeChanged " + width + "x" + height);
                if (callback != null) {
                    callback.onVideoSizeChanged(width, height);
                }
            }

            @Override
            public void onSurfaceSizeChanged(int width, int height) {
                //LLog.d(TAG, "onSurfaceSizeChanged " + width + "x" + height);
            }

            @Override
            public void onRenderedFirstFrame() {
                if (callback != null) {
                    callback.onRenderedFirstFrame();
                }
            }
        });
    }

    public void reset() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    // Internal methods.
    private MediaSource buildMediaSource(Uri uri) {
        @ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    public void pauseVideo() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    public void resumeVideo() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public long getContentPosition() {
        if (player == null) {
            return 0;
        }
        return player.getContentPosition();
    }

    public interface Callback {
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState);

        public void onVideoSizeChanged(int width, int height);

        public void onRenderedFirstFrame();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
