package vn.loitp.app.activity.customviews.wwlmusic.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFragment;
import com.core.utilities.LLog;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlmusic.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlmusic.layout.WWLMusicControlsOverlay;
import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset;

/**
 * Created by thangn on 3/1/17.
 */

@LayoutId(R.layout.wwl_music_watch_fragment)
@LogTag("WWLWatchFragment")
public class WWLWatchFragment extends BaseFragment implements TextureView.SurfaceTextureListener, WWLMusicControlsOverlay.Listener {
    private final String TAG = getClass().getSimpleName();
    private TextureView mPlayerView;
    private MediaPlayer.OnPreparedListener mOnPreparedListener = mp -> mp.start();
    private MediaPlayer mMediaPlayer;
    private Surface mSurfaceView;
    private String mUrl;
    private WWLMusicControlsOverlay mPlayerWWLMusicControlsOverlay;
    private FragmentHost mFragmentHost;
    private TextView mTitleView;
    private TextView mSubTitleView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPlayerView = getFrmRootView().findViewById(R.id.playerView);
        this.mPlayerView.requestFocus();
        this.mPlayerView.setSurfaceTextureListener(this);
        this.mPlayerWWLMusicControlsOverlay = new WWLMusicControlsOverlay(getContext());
        this.mPlayerWWLMusicControlsOverlay.setListener(this);
        ((ViewGroup) getFrmRootView()).addView(this.mPlayerWWLMusicControlsOverlay);
        this.mTitleView = getFrmRootView().findViewById(R.id.li_title);
        this.mSubTitleView = getFrmRootView().findViewById(R.id.li_subtitle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFragmentHost = (FragmentHost) activity;
    }

    public void startPlay(WWLMusicDataset.DatasetItem item) {
        this.mUrl = item.url;
        openVideo();
        this.mTitleView.setText(item.title);
        this.mSubTitleView.setText(item.subtitle);
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
        if (this.mPlayerWWLMusicControlsOverlay != null) {
            this.mPlayerWWLMusicControlsOverlay.switchFullscreen(selected);
        }
    }

    public void hideControls() {
        if (this.mPlayerWWLMusicControlsOverlay != null) {
            this.mPlayerWWLMusicControlsOverlay.hideControls();
        }
    }

    public void toggleControls() {
        if (this.mPlayerWWLMusicControlsOverlay != null) {
            this.mPlayerWWLMusicControlsOverlay.toggleControls();
        }
    }

    private void openVideo() {
        if (this.mUrl == null || this.mSurfaceView == null) {
            return;
        }

        release();
        LLog.d(TAG, "openVideo " + mUrl);

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
            LLog.e(TAG, "openVideo " + e.toString());
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
