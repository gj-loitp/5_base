package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.graphics.Color;
import android.os.Bundle;

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
        bigImageView.setColorProgressBar(Color.WHITE);
        bigImageView.load(Constants.URL_IMG_LARGE);

        //bigImageView.load(Constants.URL_IMG, Constants.URL_IMG_LARGE);
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
