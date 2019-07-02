package vn.loitp.app.activity.customviews.videoview.exoplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.exo.PlayerManager;

//custom UI exo_playback_control_view.xml
public class ExoPlayerActivity2 extends BaseFontActivity {
    private PlayerView playerView;
    private PlayerManager playerManager;
    private ImageButton exoFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = findViewById(R.id.player_view);
        playerView.setUseController(false);

        final String linkPlay = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd";
        playerManager = new PlayerManager(activity);

        final PlayerControlView controls = findViewById(R.id.controls);
        controls.setShowTimeoutMs(0);
        playerManager.init(this, playerView, linkPlay);
        controls.setPlayer(playerManager.getPlayer());

        exoFullscreen = controls.findViewById(R.id.exo_fullscreen);
        playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        exoFullscreen.setOnClickListener(view -> playerManager.toggleFullscreen(activity));
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exo_player2;
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerManager.resumeVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerManager.pauseVideo();
    }

    @Override
    public void onDestroy() {
        playerManager.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isLandscape(activity)) {
            playerManager.toggleFullscreen(activity);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        } else {
            playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        }
    }
}
