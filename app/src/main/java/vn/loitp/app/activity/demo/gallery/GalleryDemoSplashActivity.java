package vn.loitp.app.activity.demo.gallery;

import android.content.Intent;
import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LUIUtil;

import loitp.basemaster.R;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

public class GalleryDemoSplashActivity extends BaseFontActivity {
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.init(getString(R.string.flickr_URL));
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                Intent intent = new Intent(activity, GalleryDemoAlbumActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
                finish();
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
        return R.layout.activity_gallery_demo_splash;
    }
}
