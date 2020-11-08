package vn.loitp.app.activity.customviews.wwlmusic;

import android.os.Bundle;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.views.wwlmusic.layout.LWWLMusic;
import com.views.wwlmusic.utils.LWWLMusicUiUtil;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlmusic.fragments.WWLPlaylistFragment;
import vn.loitp.app.activity.customviews.wwlmusic.fragments.WWLWatchFragment;
import vn.loitp.app.activity.customviews.wwlmusic.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset;

//https://github.com/vn-ttinc/Youtube-Watch-While-Layout

@LogTag("WWLActivityMusic")
@IsFullScreen(false)
//TODO convert kotlin
public class WWLActivityMusic extends BaseFontActivity implements LWWLMusic.Listener, FragmentHost {
    private LWWLMusic LWWLMusic;
    private float mLastAlpha;
    private WWLWatchFragment watchFragment;
    private WWLPlaylistFragment playlistFragment;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_wwl_music;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.LWWLMusic = findViewById(R.id.watch_while_layout);
        this.LWWLMusic.setListener(this);

        this.watchFragment = (WWLWatchFragment) getSupportFragmentManager().findFragmentById(R.id.watch_fragment);
        this.playlistFragment = (WWLPlaylistFragment) getSupportFragmentManager().findFragmentById(R.id.playlist_fragment);
    }

    @Override
    public void WWL_onSliding(float offset) {
        float alpha;
        if (offset > 2.0f) {
            alpha = this.mLastAlpha * (3.0f - offset);
        } else {
            if (offset > 1.0f) {
                alpha = 0.25f + (0.75f * (2.0f - offset));
            } else {
                alpha = 1.0f;
            }
            if (offset >= 0.0f && offset <= 1.0f) {
                LWWLMusicUiUtil.updateStatusBarAlpha(this, 1.0f - offset);
            }
        }
        this.mLastAlpha = alpha;
    }

    @Override
    public void WWL_onClicked() {
        if (this.LWWLMusic.mState == com.views.wwlmusic.layout.LWWLMusic.STATE_MINIMIZED) {
            this.LWWLMusic.maximize(false);
        }
        if (this.LWWLMusic.mState == com.views.wwlmusic.layout.LWWLMusic.STATE_MAXIMIZED) {
            this.watchFragment.toggleControls();
        }
    }

    @Override
    public void WWL_onHided() {
        this.watchFragment.stopPlay();
    }

    @Override
    protected void onDestroy() {
        this.watchFragment.stopPlay();
        super.onDestroy();
    }

    @Override
    public void WWL_minimized() {
        this.mLastAlpha = 0.0f;
        this.watchFragment.hideControls();
    }

    @Override
    public void WWL_maximized() {
        this.mLastAlpha = 1.0f;
    }

    @Override
    public void goToDetail(WWLMusicDataset.DatasetItem item) {
        if (this.LWWLMusic.mState == com.views.wwlmusic.layout.LWWLMusic.STATE_HIDED) {
            this.LWWLMusic.mState = com.views.wwlmusic.layout.LWWLMusic.STATE_MAXIMIZED;
            this.LWWLMusic.mIsFullscreen = false;
            if (this.LWWLMusic.canAnimate()) {
                this.LWWLMusic.setAnimatePos(this.LWWLMusic.mMaxY);
            }
            this.LWWLMusic.reset();
        }
        this.LWWLMusic.maximize(false);

        this.watchFragment.startPlay(item);
        if (this.playlistFragment != null) {
            this.playlistFragment.updateItem(item);
        }
    }

    @Override
    public void onVideoCollapse() {
        LWWLMusicUiUtil.showSystemUI(this);
        this.LWWLMusic.exitFullscreenToMinimize();
        this.watchFragment.switchFullscreen(false);
        this.LWWLMusic.minimize(false);
    }

    @Override
    public void onVideoFullscreen(boolean selected) {
        if (selected) {
            LWWLMusicUiUtil.hideSystemUI(this);
            this.LWWLMusic.enterFullscreen();
        } else {
            LWWLMusicUiUtil.showSystemUI(this);
            this.LWWLMusic.exitFullscreen();
        }
        this.watchFragment.switchFullscreen(selected);
    }
}
