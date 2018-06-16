package vn.loitp.app.activity.customviews.wwlvideo.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.layout.ControlsOverlay;
import vn.loitp.app.activity.customviews.wwlvideo.utils.Dataset;

/**
 * Created by thangn on 2/26/17.
 */

public class PlayerFragment extends Fragment implements TextureView.SurfaceTextureListener, ControlsOverlay.Listener {
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
    private ControlsOverlay mPlayerControlsOverlay;
    private FragmentHost mFragmentHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wwl_video_player_fragment, container, false);
        this.mPlayerView = (TextureView) rootView.findViewById(R.id.player_view);
        this.mPlayerView.requestFocus();
        this.mPlayerView.setSurfaceTextureListener(this);
        this.mPlayerControlsOverlay = new ControlsOverlay(getContext());
        this.mPlayerControlsOverlay.setListener(this);
        ((ViewGroup) rootView).addView(this.mPlayerControlsOverlay);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mFragmentHost = (FragmentHost) activity;
    }

    public void startPlay(Dataset.DatasetItem item) {
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
        if (this.mPlayerControlsOverlay != null) {
            this.mPlayerControlsOverlay.switchFullscreen(selected);
        }
    }

    public void hideControls() {
        if (this.mPlayerControlsOverlay != null) {
            this.mPlayerControlsOverlay.hideControls();
        }
    }

    public void toggleControls() {
        if (this.mPlayerControlsOverlay != null) {
            this.mPlayerControlsOverlay.toggleControls();
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
