package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.net.Uri;
import android.os.Bundle;

import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewWithScrollViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BigImageView biv0 = findViewById(R.id.biv_0);
        biv0.setImageViewFactory(new GlideImageViewFactory());
        BigImageView biv1 = findViewById(R.id.biv_1);
        biv1.setImageViewFactory(new GlideImageViewFactory());
        BigImageView biv2 = findViewById(R.id.biv_2);
        biv2.setImageViewFactory(new GlideImageViewFactory());
        BigImageView biv3 = findViewById(R.id.biv_3);
        biv3.setImageViewFactory(new GlideImageViewFactory());

        biv0.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_LAND_S()), Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_LAND_O()));
        biv1.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LONG()));
        biv2.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_GIFT()));
        biv3.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_PORTRAIT_S()), Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_PORTRAIT_O()));
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
