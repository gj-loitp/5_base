package vn.loitp.app.activity.demo.gallery;

import android.content.Intent;
import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LUIUtil;
import com.restapi.restclient.RestClient;
import com.views.progressloadingview.avl.LAVLoadingIndicatorView;

import loitp.basemaster.R;

public class GalleryDemoSplashActivity extends BaseFontActivity {
    private LAVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.init(getString(R.string.flickr_URL));
        LUIUtil.INSTANCE.setDelay(3000, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), GalleryDemoAlbumActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(getActivity());
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
