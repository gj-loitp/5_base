package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.os.Bundle;

import com.github.piasy.biv.view.BigImageView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewWithScrollViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LBigImageView bigImageView = (LBigImageView) findViewById(R.id.mBigImage);
        //bigImageView.setColorProgressBar(Color.WHITE);
        bigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        bigImageView.load(Constants.URL_IMG_LARGE);

        LBigImageView bigImageView2 = (LBigImageView) findViewById(R.id.mBigImage2);
        //bigImageView2.setColorProgressBar(Color.RED);
        bigImageView2.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        bigImageView2.setZoomEnable(false);
        bigImageView2.load(Constants.URL_IMG_LONG);

        LBigImageView bigImageView3 = (LBigImageView) findViewById(R.id.mBigImage3);
        //bigImageView3.setColorProgressBar(Color.RED);
        bigImageView3.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        bigImageView3.setZoomEnable(false);
        bigImageView3.load(Constants.URL_IMG, Constants.URL_IMG_LONG_1);

        LBigImageView bigImageView4 = (LBigImageView) findViewById(R.id.mBigImage4);
        //bigImageView4.setColorProgressBar(Color.RED);
        bigImageView4.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        bigImageView4.setZoomEnable(false);
        bigImageView4.load(Constants.URL_IMG);
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
        return R.layout.activity_big_imageview_with_scroll_view;
    }
}
