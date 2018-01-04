package vn.loitp.app.activity.customviews.videoview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.videoview.btjzvideoplayer.JZVideoPlayerActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class VideoViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_jzvideoplayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, JZVideoPlayerActivity.class);
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
        return R.layout.activity_menu_video_view;
    }
}
