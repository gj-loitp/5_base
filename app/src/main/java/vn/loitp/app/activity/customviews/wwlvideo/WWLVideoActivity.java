package vn.loitp.app.activity.customviews.wwlvideo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseActivity;
import com.core.utilities.LLog;
import com.views.wwlmusic.utils.LWWLMusicUiUtil;
import com.views.wwlmusic.utils.LWWLMusicViewHelper;
import com.views.wwlvideo.LWWLVideo;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoMetaInfoFragment;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoPlayerFragment;
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoUpNextFragment;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

//https://github.com/vn-ttinc/Youtube-Watch-While-Layout

@LayoutId(R.layout.wwl_video_activity)
@LogTag("WWLVideoActivity")
@IsFullScreen(false)
public class WWLVideoActivity extends BaseActivity implements LWWLVideo.Listener, FragmentHost {
    private LWWLVideo LWWLVideo;
    private float mLastAlpha;
    private FrameLayout mPlayerFragmentContainer;
    private WWLVideoPlayerFragment wwlVideoPlayerFragment;
    private WWLVideoUpNextFragment wwlVideoUpNextFragment;
    private WWLVideoMetaInfoFragment wwlVideoMetaInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.LWWLVideo = findViewById(R.id.watch_while_layout);
        this.LWWLVideo.setListener(this);

        this.mPlayerFragmentContainer = findViewById(R.id.player_fragment_container);
        this.wwlVideoPlayerFragment = (WWLVideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.player_fragment);
        this.wwlVideoUpNextFragment = (WWLVideoUpNextFragment) getSupportFragmentManager().findFragmentById(R.id.up_next_fragment);
        this.wwlVideoMetaInfoFragment = (WWLVideoMetaInfoFragment) getSupportFragmentManager().findFragmentById(R.id.meta_info_fragment);
    }

    @Override
    public void WWL_onSliding(float offset) {
        LLog.d(getLogTag(), "WWL_onSliding offset " + offset);
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
        LLog.d(getLogTag(), "WWL_onClicked");
        if (this.LWWLVideo.mState == com.views.wwlvideo.LWWLVideo.STATE_MINIMIZED) {
            this.LWWLVideo.maximize(false);
        }
        if (this.LWWLVideo.mState == com.views.wwlvideo.LWWLVideo.STATE_MAXIMIZED) {
            this.wwlVideoPlayerFragment.toggleControls();
        }
    }

    @Override
    public void WWL_onHided() {
        LLog.d(getLogTag(), "WWL_onHided");
        this.wwlVideoPlayerFragment.stopPlay();
    }

    @Override
    protected void onDestroy() {
        this.wwlVideoPlayerFragment.stopPlay();
        super.onDestroy();
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
        if (this.LWWLVideo.mState == com.views.wwlvideo.LWWLVideo.STATE_HIDED) {
            this.LWWLVideo.mState = com.views.wwlvideo.LWWLVideo.STATE_MAXIMIZED;
            this.LWWLVideo.mIsFullscreen = false;
            if (this.LWWLVideo.canAnimate()) {
                this.LWWLVideo.setAnimatePos(this.LWWLVideo.mMaxY);
            }
            this.LWWLVideo.reset();
        }
        this.LWWLVideo.maximize(false);

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
        LWWLMusicUiUtil.showSystemUI(this);
        this.LWWLVideo.exitFullscreenToMinimize();
        this.wwlVideoPlayerFragment.switchFullscreen(false);
        this.LWWLVideo.minimize(false);
    }

    @Override
    public void onVideoFullscreen(boolean selected) {
        if (selected) {
            LWWLMusicUiUtil.hideSystemUI(this);
            this.LWWLVideo.enterFullscreen();
        } else {
            LWWLMusicUiUtil.showSystemUI(this);
            this.LWWLVideo.exitFullscreen();
        }
        this.wwlVideoPlayerFragment.switchFullscreen(selected);
    }

    private void updateStatusBarAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= 21) {
            int color = getResources().getColor(R.color.colorPrimaryDark);
            int color2 = Color.BLACK;
            int color3 = LWWLMusicViewHelper.evaluateColorAlpha(Math.max(0.0f, Math.min(1.0f, alpha)), color, color2);
            getWindow().setStatusBarColor(color3);
        }
    }

    private void updatePlayerAlpha(float alpha) {
        this.mPlayerFragmentContainer.setAlpha(alpha);
    }
}
