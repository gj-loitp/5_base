package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

import java.io.File;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewActivity extends BaseFontActivity {
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.hide();
        final BigImageView bigImageView = findViewById(R.id.mBigImage);
        bigImageView.setImageViewFactory(new GlideImageViewFactory());
        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
            }

            @Override
            public void onStart() {
                if (avLoadingIndicatorView != null) {
                    avLoadingIndicatorView.smoothToShow();
                }
            }

            @Override
            public void onProgress(int progress) {
                LLog.INSTANCE.d(getTAG(), "onProgress " + progress);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(File image) {
                LLog.INSTANCE.d(getTAG(), "onSuccess");
                SubsamplingScaleImageView ssiv = bigImageView.getSSIV();
                if (ssiv != null) {
                    ssiv.setZoomEnabled(true);
                }
                if (avLoadingIndicatorView != null) {
                    avLoadingIndicatorView.smoothToHide();
                }
            }

            @Override
            public void onFail(Exception error) {
            }
        });

        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_LAND_S()), Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_LAND_O()));
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_PORTRAIT_S()), Uri.parse(Constants.INSTANCE.getURL_IMG_LARGE_PORTRAIT_O()));
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_LONG()));
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.INSTANCE.getURL_IMG_GIFT()));
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
