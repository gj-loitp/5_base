package testlibuiza.uiza.com.demo;

import android.content.Intent;
import android.os.Bundle;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;
import vn.loitp.views.uizavideo.view.rl.UizaIMAVideo;

public class TestUizaVideoIMActivity1 extends BaseActivity {
    private UizaIMAVideo uizaIMAVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uizaIMAVideo = (UizaIMAVideo) findViewById(R.id.uiza_video);
        String linkPlay = getString(loitp.core.R.string.url_dash);
        String urlIMAAd = getString(loitp.core.R.string.ad_tag_url);
        String urlThumnailsPreviewSeekbar = getString(loitp.core.R.string.url_thumbnails);
        uizaIMAVideo.initData(linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
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
        return R.layout.test_uiza_ima_video_activity1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uizaIMAVideo.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uizaIMAVideo.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uizaIMAVideo.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UizaIMAVideo.CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                LLog.d(TAG, "onActivityResult RESULT_OK");
                uizaIMAVideo.initializePiP();
            } else {
                LToast.show(activity, "Draw over other app permission not available");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
