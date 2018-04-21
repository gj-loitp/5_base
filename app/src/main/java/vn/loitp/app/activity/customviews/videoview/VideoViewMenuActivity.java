package vn.loitp.app.activity.customviews.videoview;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.btjzvideoplayer.JZVideoPlayerActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2.ExoPlayer2Activity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2fullscreen.ExoPlayer2FullScreenActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel.ExoPlayer2WithDraggablePanelActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel2.ExoPlayer2WithDraggablePanel2Activity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel3.ExoPlayer2WithDraggablePanel3Activity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.ExoPlayer2WithPreviewSeekbar;
import vn.loitp.app.activity.customviews.videoview.exoplayerdemofromgg.ExoPlayerDemoFromGGActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayerdemofromggima.ExoPlayerDemoFromGGIMActivity;
import vn.loitp.app.activity.customviews.videoview.pipvideo.PiPVideoActivity;
import vn.loitp.app.activity.customviews.videoview.uizavideo.UizaVideoActivity;
import vn.loitp.app.activity.customviews.videoview.uizavideowithima.UizaVideoIMActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.views.LToast;

public class VideoViewMenuActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_jzvideoplayer).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_with_dragpanel).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_with_dragpanel_2).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_with_dragpanel_3).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_with_preview_seekbar).setOnClickListener(this);
        findViewById(R.id.bt_pip_video).setOnClickListener(this);
        findViewById(R.id.bt_uiza_video).setOnClickListener(this);
        findViewById(R.id.bt_exo_player2_from_gg).setOnClickListener(this);
        findViewById(R.id.bt_exo_player2_from_gg_ima).setOnClickListener(this);
        findViewById(R.id.bt_uiza_video_ima).setOnClickListener(this);
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
        return R.layout.activity_menu_video_view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_jzvideoplayer:
                intent = new Intent(activity, JZVideoPlayerActivity.class);
                break;
            case R.id.bt_exoplayer2:
                intent = new Intent(activity, ExoPlayer2Activity.class);
                break;
            case R.id.bt_exoplayer2_with_dragpanel:
                intent = new Intent(activity, ExoPlayer2WithDraggablePanelActivity.class);
                break;
            case R.id.bt_exoplayer2_with_dragpanel_2:
                intent = new Intent(activity, ExoPlayer2WithDraggablePanel2Activity.class);
                break;
            case R.id.bt_exoplayer2_with_dragpanel_3:
                intent = new Intent(activity, ExoPlayer2WithDraggablePanel3Activity.class);
                break;
            case R.id.bt_exoplayer2_fullscreen:
                intent = new Intent(activity, ExoPlayer2FullScreenActivity.class);
                break;
            case R.id.bt_exoplayer2_with_preview_seekbar:
                intent = new Intent(activity, ExoPlayer2WithPreviewSeekbar.class);
                break;
            case R.id.bt_uiza_video:
                intent = new Intent(activity, UizaVideoActivity.class);
                break;
            case R.id.bt_exo_player2_from_gg:
                intent = new Intent(activity, ExoPlayerDemoFromGGActivity.class);
                break;
            case R.id.bt_exo_player2_from_gg_ima:
                intent = new Intent(activity, ExoPlayerDemoFromGGIMActivity.class);
                break;
            case R.id.bt_uiza_video_ima:
                intent = new Intent(activity, UizaVideoIMActivity.class);
                break;
            case R.id.bt_pip_video:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent = new Intent(activity, PiPVideoActivity.class);
                } else {
                    LToast.show(activity, "Only support >= Build.VERSION_CODES.O");
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
