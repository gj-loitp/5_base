package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circularroundrectimageview.CircularRoundRectImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.img2ascii.Img2AsciiActivity;
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BigImageView bigImageView = (BigImageView) findViewById(R.id.mBigImage);
        //bigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_START);
        bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG_1));

        // Or show a thumbnail before the big image is loaded
        //bigImageView.showImage(Uri.parse("https://image.flaticon.com/icons/svg/265/265674.svg"), Uri.parse(Constants.URL_IMG_LONG_1));
        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(File image) {
                LLog.d(TAG, "Image was found in the cache");
            }

            @Override
            public void onCacheMiss(File image) {
                LLog.d(TAG, "Image was downloaded from the network");
            }

            @Override
            public void onStart() {
                LLog.d(TAG, "Image download has started");
            }

            @Override
            public void onProgress(int progress) {
                LLog.d(TAG, "Image download progress has changed " + progress);
            }

            @Override
            public void onFinish() {
                LLog.d(TAG, "Image download has finished");
            }

            @Override
            public void onSuccess(File image) {
                LLog.d(TAG, "Image was retrieved successfully (either from cache or network)");
            }

            @Override
            public void onFail(Exception error) {
                LLog.d(TAG, "Image download failed " + error);
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
