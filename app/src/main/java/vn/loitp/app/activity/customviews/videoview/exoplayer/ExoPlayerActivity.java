package vn.loitp.app.activity.customviews.videoview.exoplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LScreenUtil;
import com.google.android.exoplayer2.ui.PlayerView;
import com.views.exo.PlayerManager;

import loitp.basemaster.R;

//custom UI exo_playback_control_view.xml
public class ExoPlayerActivity extends BaseFontActivity {
    private PlayerView playerView;
    private PlayerManager playerManager;
    private String linkPlay = "";
    private ImageButton exoFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = findViewById(R.id.player_view);
        exoFullscreen = findViewById(R.id.exo_fullscreen);
        linkPlay = getIntent().getStringExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_PLAY());
        final String linkIMAAd = getIntent().getStringExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_IMA_AD());
        if (linkIMAAd == null) {
            playerManager = new PlayerManager(getActivity());
        } else {
            playerManager = new PlayerManager(getActivity(), linkIMAAd);
        }
        playerManager.updateSizePlayerView(getActivity(), playerView, exoFullscreen);
        exoFullscreen.setOnClickListener(view -> playerManager.toggleFullscreen(getActivity()));
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
        return R.layout.activity_exo_player;
    }

    @Override
    public void onResume() {
        super.onResume();
        playerManager.init(this, playerView, linkPlay);
    }

    @Override
    public void onPause() {
        super.onPause();
        playerManager.reset();
    }

    @Override
    public void onDestroy() {
        playerManager.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.INSTANCE.isLandscape(getActivity())) {
            playerManager.toggleFullscreen(getActivity());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager.updateSizePlayerView(getActivity(), playerView, exoFullscreen);
        } else {
            playerManager.updateSizePlayerView(getActivity(), playerView, exoFullscreen);
        }
    }
}
