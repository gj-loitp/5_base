package vn.loitp.app.activity.customviews.videoview.uizavideowithima;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.uizavideo.FrmUizaIMAVideo;

public class UizaVideoIMActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        FrmUizaIMAVideo frmUizaIMAVideo = new FrmUizaIMAVideo();
        LScreenUtil.replaceFragment(activity, fl.getId(), frmUizaIMAVideo, false);
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
        return R.layout.uiza_ima_video_activity;
    }
}
