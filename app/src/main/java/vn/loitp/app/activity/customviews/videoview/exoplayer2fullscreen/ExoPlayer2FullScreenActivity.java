package vn.loitp.app.activity.customviews.videoview.exoplayer2fullscreen;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDisplayUtils;
import vn.loitp.core.utilities.LLog;

public class ExoPlayer2FullScreenActivity extends BaseActivity implements View.OnClickListener {
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private TextView tv;
    private ImageView mFullScreenIcon;
    private FrameLayout mFullScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_m3u8).setOnClickListener(this);
        findViewById(R.id.bt_mp3).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);


        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
        playM3u8();
        //playMp3();

        initExoPlayer();

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = LDisplayUtils.getDialogW(activity) * 10 / 16;
        mExoPlayerView.setLayoutParams(params);

        mFullScreenIcon = mExoPlayerView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = mExoPlayerView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLog.d(TAG, "onClick mFullScreenButton");
                LActivityUtil.toggleScreenOritation(activity);
                LActivityUtil.toggleFullScreen(activity);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exo_player2_fullscreen;
    }

    private void initExoPlayer() {
        LLog.d(TAG, "initExoPlayer");
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);
        ((SimpleExoPlayer)mExoPlayerView.getPlayer()).prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);
        mExoPlayerView.getPlayer().addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                //do nothing
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                //do nothing
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        //LLog.d(TAG, "onPlayerStateChanged STATE_BUFFERING");
                        break;
                    case Player.STATE_ENDED:
                        //LLog.d(TAG, "onPlayerStateChanged STATE_ENDED");
                        break;
                    case Player.STATE_IDLE:
                        //LLog.d(TAG, "onPlayerStateChanged STATE_IDLE");
                        break;
                    case Player.STATE_READY:
                        //LLog.d(TAG, "onPlayerStateChanged STATE_READY");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                //do nothing
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                //do nothing
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                //do nothing
            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }


    private void playM3u8() {
        String streamUrl = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
        tv.setText(streamUrl);
        String userAgent = Util.getUserAgent(activity, getApplicationContext().getApplicationInfo().packageName);
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(activity, null, httpDataSourceFactory);
        Uri daUri = Uri.parse(streamUrl);
        mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
    }

    private void playMp3() {
        Handler mHandler = new Handler();
        String url = "http://www.hoahaomedia.org/hoahao/_media/PGHH/Audio/Sam_Giang/Hue_Duyen/001_Sam%20Giang%20Khuyen%20Nguoi%20Doi%20Tu%20Niem%20Hue%20Duyen%20-%20Tri%20Tung.mp3";
        tv.setText(url);
        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(activity, getApplicationContext().getApplicationInfo().packageName);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);
        mVideoSource = new ExtractorMediaSource(uri, dataSourceFactory, Mp3Extractor.FACTORY, mHandler, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_m3u8:
                mExoPlayerView.getPlayer().release();
                playM3u8();
                initExoPlayer();
                break;
            case R.id.bt_mp3:
                mExoPlayerView.getPlayer().release();
                playMp3();
                initExoPlayer();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mExoPlayerView.setLayoutParams(params);
            LLog.d(TAG, "ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = LDisplayUtils.getDialogW(activity) * 9 / 16;
            mExoPlayerView.setLayoutParams(params);
            LLog.d(TAG, "ORIENTATION_PORTRAIT");
        }
    }

    @Override
    protected void onDestroy() {
        mExoPlayerView.getPlayer().release();
        super.onDestroy();
    }
}
