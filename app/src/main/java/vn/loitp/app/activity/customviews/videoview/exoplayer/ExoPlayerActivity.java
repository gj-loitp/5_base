package vn.loitp.app.activity.customviews.videoview.exoplayer;

import android.os.Bundle;

import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;

public class ExoPlayerActivity extends BaseFontActivity {
    private PlayerView playerView;
    private PlayerManager player;
    private String linkPlay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = findViewById(R.id.player_view);

        linkPlay = getIntent().getStringExtra(Constants.KEY_VIDEO_LINK_PLAY);
        String linkIMAAd = getIntent().getStringExtra(Constants.KEY_VIDEO_LINK_IMA_AD);

        if (linkIMAAd == null) {
            player = new PlayerManager(activity);
        } else {
            player = new PlayerManager(activity, linkIMAAd);
        }
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
        player.init(this, playerView, linkPlay);
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
}
