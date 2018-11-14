package vn.loitp.app.activity.customviews.videoview.uzvideo;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.uzvideo.UZVideo;

//custom UI exo_playback_control_view.xml
public class UZActivity extends BaseFontActivity {
    private UZVideo uzVideo;
    private String entityIdDefaultVOD = "7699e10e-5ce3-4dab-a5ad-a615a711101e";
    private String entityIdDefaultLIVE = "1759f642-e062-4e88-b5f2-e3022bd03b57";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uzVideo = (UZVideo) findViewById(R.id.uz_video);
        findViewById(R.id.bt_vod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uzVideo.play(entityIdDefaultVOD);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_uz;
    }

    @Override
    public void onResume() {
        super.onResume();
        uzVideo.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uzVideo.onPause();
    }

    @Override
    public void onDestroy() {
        uzVideo.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isFullScreen(activity)) {
            uzVideo.toggleFullscreen();
        } else {
            super.onBackPressed();
        }
    }
}
