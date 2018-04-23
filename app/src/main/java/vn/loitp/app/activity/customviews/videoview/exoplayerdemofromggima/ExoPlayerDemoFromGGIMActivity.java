package vn.loitp.app.activity.customviews.videoview.exoplayerdemofromggima;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.views.uizavideo.view.util.UizaUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LScreenUtil;

public class ExoPlayerDemoFromGGIMActivity extends BaseActivity implements PreviewView.OnPreviewChangeListener, View.OnClickListener {
    private PlayerView playerView;
    private PlayerManager player;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageView exoFullscreenIcon;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);

        imageView = (ImageView) findViewById(R.id.imageView);

        player = new PlayerManager(activity, previewTimeBarLayout, imageView, getString(R.string.url_thumbnails));
        previewTimeBarLayout.setPreviewLoader(player);

        playerView.findViewById(R.id.exo_fullscreen_button).setOnClickListener(this);
        exoFullscreenIcon = (ImageView) playerView.findViewById(R.id.exo_fullscreen_icon);

        UizaUtil.resizeLayout(playerView, null);
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
        return R.layout.uiza_ima_video_core_frm;
    }

    @Override
    public void onResume() {
        super.onResume();
        player.init(this, playerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.reset();
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }

    @Override
    public void onStartPreview(PreviewView previewView) {
        //
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        //
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exo_fullscreen_button:
                UizaUtil.setUIFullScreenIcon(activity, exoFullscreenIcon);
                LActivityUtil.toggleScreenOritation(activity);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (activity != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LScreenUtil.hideDefaultControls(activity);
            } else {
                LScreenUtil.showDefaultControls(activity);
            }
        }
        UizaUtil.resizeLayout(playerView, null);
    }
}
