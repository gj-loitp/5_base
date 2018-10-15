package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;

import java.io.File;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

//https://github.com/Piasy/BigImageViewer
public class BigImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
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
            }

            @Override
            public void onProgress(int progress) {
                LLog.d(TAG, "onProgress " + progress);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(File image) {
                LLog.d(TAG, "onSuccess");
                SubsamplingScaleImageView ssiv = bigImageView.getSSIV();
                if (ssiv != null) {
                    ssiv.setZoomEnabled(true);
                }
            }

            @Override
            public void onFail(Exception error) {
            }
        });

        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O));
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O));
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG));
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT));
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
