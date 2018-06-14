package vn.loitp.views.imageview.bigimageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;

//https://github.com/Piasy/BigImageViewer
public class LBigImageView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private BigImageView bigImageView;

    public LBigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LBigImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_big_image_view, this);
        bigImageView = (BigImageView) findViewById(R.id.biv);
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

    public void load(String url) {
        bigImageView.showImage(Uri.parse(url));
    }

    public void load(String thumnail, String url) {
        // Or show a thumbnail before the big image is loaded
        bigImageView.showImage(Uri.parse(thumnail), Uri.parse(url));
    }

    public BigImageView getLBigImageView() {
        return bigImageView;
    }

    public void setInitScaleType(int type) {
        //setInitScaleType(BigImageView.INIT_SCALE_TYPE_START)
        bigImageView.setInitScaleType(type);
    }

    public void setFailureImage(Drawable failureImage) {
        bigImageView.setFailureImage(failureImage);
    }

    public void setFailureImageInitScaleType(ImageView.ScaleType scaleType) {
        bigImageView.setFailureImageInitScaleType(scaleType);
    }

    public void setOptimizeDisplay(boolean optimizeDisplay) {
        bigImageView.setOptimizeDisplay(optimizeDisplay);
    }
}
