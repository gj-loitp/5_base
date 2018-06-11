package vn.loitp.core.loitp.gallery;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.restclient.RestClient;

public class GalleryCoreSplashActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.init(getString(R.string.flickr_URL));

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        TextView tvCopyright = (TextView) findViewById(R.id.tv_copyright);
        LUIUtil.setTextShadow(tvCopyright);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        LUIUtil.setTextShadow(tvName);

        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                /*Intent intent = new Intent(activity, GalleryCoreAlbumActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
                finish();*/
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_core_splash;
    }
}
