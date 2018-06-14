package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.net.Uri;
import android.os.Bundle;

import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LBigImageView bigImageView = (LBigImageView) findViewById(R.id.mBigImage);
        bigImageView.load(Constants.URL_IMG_LONG_1);
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
