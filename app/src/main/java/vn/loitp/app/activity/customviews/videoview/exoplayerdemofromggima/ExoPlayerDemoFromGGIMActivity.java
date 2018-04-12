package vn.loitp.app.activity.customviews.videoview.exoplayerdemofromggima;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;

public class ExoPlayerDemoFromGGIMActivity extends BaseActivity implements PreviewView.OnPreviewChangeListener {
    private PlayerView playerView;
    private PlayerManager player;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        player = new PlayerManager(activity, previewTimeBarLayout, (ImageView) findViewById(R.id.imageView), getString(R.string.url_thumbnails));
        previewTimeBarLayout.setPreviewLoader(player);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exo_player2_demo_from_gg_ima;
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
        /*if (getResources().getBoolean(R.bool.landscape)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }*/
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        /*if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.stopPreview();
        }*/
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {

    }
}
