package vn.loitp.app.activity.customviews.wwlmusic;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlmusic.fragments.WWLPlaylistFragment;
import vn.loitp.app.activity.customviews.wwlmusic.fragments.WWLWatchFragment;
import vn.loitp.app.activity.customviews.wwlmusic.interfaces.FragmentHost;
import vn.loitp.views.wwlmusic.layout.WWLMusic;
import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset;
import vn.loitp.views.wwlmusic.utils.WWLMusicUiUtil;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/vn-ttinc/Youtube-Watch-While-Layout

public class WWLActivityMusic extends BaseFontActivity implements WWLMusic.Listener, FragmentHost {
    private WWLMusic wwlMusic;
    private float mLastAlpha;
    private WWLWatchFragment watchFragment;
    private WWLPlaylistFragment playlistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.wwlMusic = (WWLMusic) findViewById(R.id.watch_while_layout);
        this.wwlMusic.setListener(this);

        this.watchFragment = (WWLWatchFragment) getSupportFragmentManager().findFragmentById(R.id.watch_fragment);
        this.playlistFragment = (WWLPlaylistFragment) getSupportFragmentManager().findFragmentById(R.id.playlist_fragment);
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
        return R.layout.activity_wwl_music;
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
                WWLMusicUiUtil.updateStatusBarAlpha(activity, 1.0f - offset);
            }
        }
        this.mLastAlpha = alpha;
    }

    @Override
    public void WWL_onClicked() {
        if (this.wwlMusic.mState == WWLMusic.STATE_MINIMIZED) {
            this.wwlMusic.maximize(false);
        }
        if (this.wwlMusic.mState == WWLMusic.STATE_MAXIMIZED) {
            this.watchFragment.toggleControls();
        }
    }

    @Override
    public void WWL_onHided() {
        this.watchFragment.stopPlay();
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
        if (this.wwlMusic.mState == WWLMusic.STATE_HIDED) {
            this.wwlMusic.mState = WWLMusic.STATE_MAXIMIZED;
            this.wwlMusic.mIsFullscreen = false;
            if (this.wwlMusic.canAnimate()) {
                this.wwlMusic.setAnimatePos(this.wwlMusic.mMaxY);
            }
            this.wwlMusic.reset();
        }
        this.wwlMusic.maximize(false);

        this.watchFragment.startPlay(item);
        if (this.playlistFragment != null) {
            this.playlistFragment.updateItem(item);
        }
    }

    @Override
    public void onVideoCollapse() {
        WWLMusicUiUtil.showSystemUI(activity);
        this.wwlMusic.exitFullscreenToMinimize();
        this.watchFragment.switchFullscreen(false);
        this.wwlMusic.minimize(false);
    }

    @Override
    public void onVideoFullscreen(boolean selected) {
        if (selected) {
            WWLMusicUiUtil.hideSystemUI(activity);
            this.wwlMusic.enterFullscreen();
        } else {
            WWLMusicUiUtil.showSystemUI(activity);
            this.wwlMusic.exitFullscreen();
        }
        this.watchFragment.switchFullscreen(selected);
    }
}
