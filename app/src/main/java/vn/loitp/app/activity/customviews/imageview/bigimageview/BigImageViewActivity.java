package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LBigImageView bigImageView = (LBigImageView) findViewById(R.id.mBigImage);
        //bigImageView.setZoomEnable(true);
        //bigImageView.setColorProgressBar(Color.WHITE);
        //bigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.load(Constants.URL_IMG_LARGE_LAND_S, Constants.URL_IMG_LARGE_LAND_O);
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.load(Constants.URL_IMG_LARGE_PORTRAIT_S, Constants.URL_IMG_LARGE_PORTRAIT_O);
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.load(Constants.URL_IMG_GIFT);
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
        return R.layout.activity_big_imageview;
    }
}
