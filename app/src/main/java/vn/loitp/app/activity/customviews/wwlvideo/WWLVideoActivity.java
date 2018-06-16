package vn.loitp.app.activity.customviews.wwlvideo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoMetaInfoFragment;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoPlayerFragment;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoUpNextFragment;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.wwlmusic.utils.WWLMusicUiUtil;
import vn.loitp.views.wwlmusic.utils.WWLMusicViewHelper;
import vn.loitp.views.wwlvideo.layout.WWLVideo;

//https://github.com/vn-ttinc/Youtube-Watch-While-Layout
public class WWLVideoActivity extends BaseActivity implements WWLVideo.Listener, FragmentHost {
    private WWLVideo wwlVideo;
    private float mLastAlpha;
    private FrameLayout mPlayerFragmentContainer;
    private WWLVideoPlayerFragment wwlVideoPlayerFragment;
    private WWLVideoUpNextFragment wwlVideoUpNextFragment;
    private WWLVideoMetaInfoFragment wwlVideoMetaInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.wwlVideo = (WWLVideo) findViewById(R.id.watch_while_layout);
        this.wwlVideo.setListener(this);

        this.mPlayerFragmentContainer = (FrameLayout) findViewById(R.id.player_fragment_container);
        this.wwlVideoPlayerFragment = (WWLVideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.player_fragment);
        this.wwlVideoUpNextFragment = (WWLVideoUpNextFragment) getSupportFragmentManager().findFragmentById(R.id.up_next_fragment);
        this.wwlVideoMetaInfoFragment = (WWLVideoMetaInfoFragment) getSupportFragmentManager().findFragmentById(R.id.meta_info_fragment);
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
        return R.layout.wwl_video_activity;
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
                updateStatusBarAlpha(1.0f - offset);
            }
        }
        updatePlayerAlpha(alpha);
        this.mLastAlpha = alpha;
    }

    @Override
    public void WWL_onClicked() {
        if (this.wwlVideo.mState == WWLVideo.STATE_MINIMIZED) {
            this.wwlVideo.maximize(false);
        }
        if (this.wwlVideo.mState == WWLVideo.STATE_MAXIMIZED) {
            this.wwlVideoPlayerFragment.toggleControls();
        }
    }

    @Override
    public void WWL_onHided() {
        this.wwlVideoPlayerFragment.stopPlay();
    }

    @Override
    public void WWL_minimized() {
        this.mLastAlpha = 0.0f;
        this.wwlVideoPlayerFragment.hideControls();
    }

    @Override
    public void WWL_maximized() {
        this.mLastAlpha = 1.0f;
    }

    @Override
    public void goToDetail(WWLVideoDataset.DatasetItem item) {
        if (this.wwlVideo.mState == WWLVideo.STATE_HIDED) {
            this.wwlVideo.mState = WWLVideo.STATE_MAXIMIZED;
            this.wwlVideo.mIsFullscreen = false;
            if (this.wwlVideo.canAnimate()) {
                this.wwlVideo.setAnimatePos(this.wwlVideo.mMaxY);
            }
            this.wwlVideo.reset();
        }
        this.wwlVideo.maximize(false);

        this.wwlVideoPlayerFragment.startPlay(item);
        if (this.wwlVideoUpNextFragment != null) {
            this.wwlVideoUpNextFragment.updateItem(item);
        }
        if (this.wwlVideoMetaInfoFragment != null) {
            this.wwlVideoMetaInfoFragment.updateItem(item);
        }
    }

    @Override
    public void onVideoCollapse() {
        WWLMusicUiUtil.showSystemUI(activity);
        this.wwlVideo.exitFullscreenToMinimize();
        this.wwlVideoPlayerFragment.switchFullscreen(false);
        this.wwlVideo.minimize(false);
    }

    @Override
    public void onVideoFullscreen(boolean selected) {
        if (selected) {
            WWLMusicUiUtil.hideSystemUI(activity);
            this.wwlVideo.enterFullscreen();
        } else {
            WWLMusicUiUtil.showSystemUI(activity);
            this.wwlVideo.exitFullscreen();
        }
        this.wwlVideoPlayerFragment.switchFullscreen(selected);
    }

    private void updateStatusBarAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= 21) {
            int color = getResources().getColor(R.color.colorPrimaryDark);
            int color2 = Color.BLACK;
            int color3 = WWLMusicViewHelper.evaluateColorAlpha(Math.max(0.0f, Math.min(1.0f, alpha)), color, color2);
            getWindow().setStatusBarColor(color3);
        }
    }

    private void updatePlayerAlpha(float alpha) {
        this.mPlayerFragmentContainer.setAlpha(alpha);
    }
}
