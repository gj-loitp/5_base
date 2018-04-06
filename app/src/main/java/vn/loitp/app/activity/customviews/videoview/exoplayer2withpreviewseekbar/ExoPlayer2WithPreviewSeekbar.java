package vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.PBLocalActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.PBMainActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class ExoPlayer2WithPreviewSeekbar extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                Intent intent = new Intent(activity, PBMainActivity.class);
                //Intent intent = new Intent(activity, PBLocalActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
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
        return R.layout.activity_exo_player2_with_preview_seekbar;
    }
}
