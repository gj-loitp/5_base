package vn.loitp.app.activity.customviews.watchwhilelayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.watchwhilelayout.fragments.PlaylistFragment;
import vn.loitp.app.activity.customviews.watchwhilelayout.fragments.WatchFragment;
import vn.loitp.app.activity.customviews.watchwhilelayout.layout.WatchWhileLayout;
import vn.loitp.app.activity.customviews.watchwhilelayout.utils.Dataset;
import vn.loitp.core.base.BaseFontActivity;

public class WWLActivityMusic extends BaseFontActivity implements WatchWhileLayout.Listener, FragmentHost {
    private WatchWhileLayout mWatchWhileLayout;
    private float mLastAlpha;
    private WatchFragment mPlayerFragment;
    private PlaylistFragment mPlaylistFragment;

    private static int evaluateColorAlpha(float f, int color1, int color2) {
        int c14 = color1 >>> 24;
        int c13 = (color1 >> 16) & 255;
        int c12 = (color1 >> 8) & 255;
        int c11 = color1 & 255;
        int c24 = color2 >>> 24;
        int c23 = (color2 >> 16) & 255;
        int c22 = (color2 >> 8) & 255;
        int c21 = color2 & 255;
        return (c11 + ((int) (((float) (c21 - c11)) * f))) | ((((c14 + ((int) (((float) (c24 - c14)) * f))) << 24) | ((c13 + ((int) (((float) (c23 - c13)) * f))) << 16)) | ((((int) (((float) (c22 - c12)) * f)) + c12) << 8));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        this.mWatchWhileLayout = (WatchWhileLayout) findViewById(R.id.watch_while_layout);
        this.mWatchWhileLayout.setListener(this);

        this.mPlayerFragment = (WatchFragment) getSupportFragmentManager().findFragmentById(R.id.watch_fragment);
        this.mPlaylistFragment = (PlaylistFragment) getSupportFragmentManager().findFragmentById(R.id.playlist_fragment);
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
                updateStatusBarAlpha(1.0f - offset);
            }
        }
        this.mLastAlpha = alpha;
    }

    @Override
    public void WWL_onClicked() {
        if (this.mWatchWhileLayout.mState == WatchWhileLayout.STATE_MINIMIZED) {
            this.mWatchWhileLayout.maximize(false);
        }
        if (this.mWatchWhileLayout.mState == WatchWhileLayout.STATE_MAXIMIZED) {
            this.mPlayerFragment.toggleControls();
        }
    }

    @Override
    public void WWL_onHided() {
        this.mPlayerFragment.stopPlay();
    }

    @Override
    public void WWL_minimized() {
        this.mLastAlpha = 0.0f;
        this.mPlayerFragment.hideControls();
    }

    @Override
    public void WWL_maximized() {
        this.mLastAlpha = 1.0f;
    }

    @Override
    public void goToDetail(Dataset.DatasetItem item) {
        if (this.mWatchWhileLayout.mState == WatchWhileLayout.STATE_HIDED) {
            this.mWatchWhileLayout.mState = WatchWhileLayout.STATE_MAXIMIZED;
            this.mWatchWhileLayout.mIsFullscreen = false;
            if (this.mWatchWhileLayout.canAnimate()) {
                this.mWatchWhileLayout.setAnimatePos(this.mWatchWhileLayout.mMaxY);
            }
            this.mWatchWhileLayout.reset();
        }
        this.mWatchWhileLayout.maximize(false);

        this.mPlayerFragment.startPlay(item);
        if (this.mPlaylistFragment != null) {
            this.mPlaylistFragment.updateItem(item);
        }
    }

    @Override
    public void onVideoCollapse() {
        showSystemUI();
        this.mWatchWhileLayout.exitFullscreenToMinimize();
        this.mPlayerFragment.switchFullscreen(false);
        this.mWatchWhileLayout.minimize(false);
    }

    @Override
    public void onVideoFullscreen(boolean selected) {
        if (selected) {
            hideSystemUI();
            this.mWatchWhileLayout.enterFullscreen();
        } else {
            showSystemUI();
            this.mWatchWhileLayout.exitFullscreen();
        }
        this.mPlayerFragment.switchFullscreen(selected);
    }

    private void updateStatusBarAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= 21) {
            int color = getResources().getColor(R.color.colorPrimaryDark);
            int color2 = Color.BLACK;
            int color3 = evaluateColorAlpha(Math.max(0.0f, Math.min(1.0f, alpha)), color, color2);
            getWindow().setStatusBarColor(color3);
        }
    }

    private void hideSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!ViewConfiguration.get(this).hasPermanentMenuKey()) {
            int newUiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= 19) {
                newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

    private void showSystemUI() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!ViewConfiguration.get(this).hasPermanentMenuKey()) {
            int newUiOptions = View.VISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }
}
