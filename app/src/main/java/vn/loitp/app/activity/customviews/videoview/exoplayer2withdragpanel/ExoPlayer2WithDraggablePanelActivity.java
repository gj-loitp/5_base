package vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.ui.PlaybackControlView;
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
import vn.loitp.core.utilities.LDisplayUtils;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.draggablepanel.DraggableListener;
import vn.loitp.views.layout.draggablepanel.DraggableView;

public class ExoPlayer2WithDraggablePanelActivity extends BaseActivity implements View.OnClickListener {
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;

    private TextView tv;
    private DraggableView draggableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        findViewById(R.id.bt_m3u8).setOnClickListener(this);
        findViewById(R.id.bt_mp3).setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);

        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);

        draggableView.setClickToMaximizeEnabled(false);
        draggableView.setClickToMinimizeEnabled(false);
        draggableView.setHorizontalAlphaEffectEnabled(true);
        draggableView.setTouchEnabled(false);

        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                LLog.d(TAG, "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.d(TAG, "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.d(TAG, "onClosedToLeft");
                releaseVideo();
            }

            @Override
            public void onClosedToRight() {
                LLog.d(TAG, "onClosedToRight");
                releaseVideo();
            }
        });

        setSizePlayer();

        initFullscreenDialog();
        initFullscreenButton();

        playM3u8();
        //playMp3();
        initExoPlayer();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exo_player2_with_draggable_panel;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        setSizePlayer();
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {
        //PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = mExoPlayerView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = mExoPlayerView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    private void initExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

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
                        LLog.d(TAG, "onPlayerStateChanged STATE_BUFFERING");
                        break;
                    case Player.STATE_ENDED:
                        LLog.d(TAG, "onPlayerStateChanged STATE_ENDED");
                        break;
                    case Player.STATE_IDLE:
                        LLog.d(TAG, "onPlayerStateChanged STATE_IDLE");
                        break;
                    case Player.STATE_READY:
                        LLog.d(TAG, "onPlayerStateChanged STATE_READY");
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

    private void setSizePlayer() {
        if (mExoPlayerView == null) {
            return;
        }
        int screenW = LDisplayUtils.getScreenW(activity);
        mExoPlayerView.getLayoutParams().width = screenW;
        mExoPlayerView.getLayoutParams().height = screenW * 9 / 16;
        mExoPlayerView.requestLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
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
    protected void onPause() {
        super.onPause();
        mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
        mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

        if (mFullScreenDialog != null) {
            mFullScreenDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        releaseVideo();
        super.onDestroy();
    }

    private void releaseVideo() {
        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mExoPlayerView.getPlayer().release();
            LToast.show(activity, "releaseVideo();");
        }
    }

    @Override
    public void onClick(View v) {
        mExoPlayerView.getPlayer().release();
        switch (v.getId()) {
            case R.id.bt_m3u8:
                playM3u8();
                initExoPlayer();
                break;
            case R.id.bt_mp3:
                playMp3();
                initExoPlayer();
                break;
        }
    }
}
