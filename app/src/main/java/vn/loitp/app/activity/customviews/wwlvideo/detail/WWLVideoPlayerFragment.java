package vn.loitp.app.activity.customviews.wwlvideo.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.layout.WWLVideoControlsOverlay;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by thangn on 2/26/17.
 */

public class WWLVideoPlayerFragment extends BaseFragment implements TextureView.SurfaceTextureListener, WWLVideoControlsOverlay.Listener {
    private TextureView mPlayerView;
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    };
    private MediaPlayer mMediaPlayer;
    private Surface mSurfaceView;
    private String mUrl;
    private WWLVideoControlsOverlay mPlayerWWLVideoControlsOverlay;
    private FragmentHost mFragmentHost;

    @Nullable

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPlayerView = (TextureView) frmRootView.findViewById(R.id.player_view);
        this.mPlayerView.requestFocus();
        this.mPlayerView.setSurfaceTextureListener(this);
        this.mPlayerWWLVideoControlsOverlay = new WWLVideoControlsOverlay(getContext());
        this.mPlayerWWLVideoControlsOverlay.setListener(this);
        ((ViewGroup) frmRootView).addView(this.mPlayerWWLVideoControlsOverlay);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_video_player_fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFragmentHost = (FragmentHost) activity;
    }

    public void startPlay(WWLVideoDataset.DatasetItem item) {
        this.mUrl = item.url;
        openVideo();
    }

    public void stopPlay() {
        if (this.mUrl != null) {
            release();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            this.mSurfaceView = new Surface(surface);
            openVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.mSurfaceView = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void CO_doCollapse() {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.onVideoCollapse();
        }
    }

    @Override
    public void CO_doFullscreen(boolean selected) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.onVideoFullscreen(selected);
        }
    }

    public void switchFullscreen(boolean selected) {
        if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.switchFullscreen(selected);
        }
    }

    public void hideControls() {
        if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.hideControls();
        }
    }

    public void toggleControls() {
        if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.toggleControls();
        }
    }

    private void openVideo() {
        if (this.mUrl == null || this.mSurfaceView == null) {
            return;
        }

        release();

        AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        try {
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setOnPreparedListener(this.mOnPreparedListener);
            this.mMediaPlayer.setDataSource(getContext(), Uri.parse(this.mUrl));
            this.mMediaPlayer.setSurface(this.mSurfaceView);
            this.mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            this.mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void release() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;

            AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
    }
}
