package vn.loitp.app.activity.customviews.videoview;

import android.app.Activity;
import android.content.Intent;
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
import vn.loitp.app.activity.customviews.videoview.pipvideo.PiPVideoActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

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
            case R.id.bt_pip_video:
                intent = new Intent(activity, PiPVideoActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
