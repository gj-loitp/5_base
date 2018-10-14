package vn.loitp.views.imageview.bigimageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LUIUtil;

//https://github.com/Piasy/BigImageViewer
public class LBigImageView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private BigImageView bigImageView;
    private TextView tvProgress;
    private ProgressBar progressBar;

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
        bigImageView = (BigImageView) findViewById(R.id.b_iv);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setTextShadow(tvProgress);
        bigImageView.setImageViewFactory(new GlideImageViewFactory());

        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
                LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
                LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onProgress(int progress) {
                tvProgress.setText(progress + "");
            }

            @Override
            public void onFinish() {
                tvProgress.setVisibility(GONE);
                LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onSuccess(File image) {
                tvProgress.setVisibility(GONE);
                LUIUtil.setProgressBarVisibility(progressBar, GONE);
                if (callback != null) {
                    callback.onSuccess(image);
                }
            }

            @Override
            public void onFail(Exception error) {
                //LLog.d(TAG, "Image download failed " + error);
                tvProgress.setVisibility(VISIBLE);
                LAnimationUtil.play(tvProgress, Techniques.Pulse);
                tvProgress.setText("Error");
                LUIUtil.setProgressBarVisibility(progressBar, GONE);
                if (callback != null) {
                    callback.onFail(error);
                }
            }
        });
    }

    public interface Callback {
        public void onSuccess(File image);

        public void onFail(Exception error);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setZoomEnable(boolean isEnable) {
        if (getSSIV() != null) {
            getSSIV().setZoomEnabled(isEnable);
        }
    }

    public SubsamplingScaleImageView getSSIV() {
        return bigImageView.getSSIV();
    }

    public void clear() {
        bigImageView.cancel();
        getSSIV().recycle();
    }

    public void load(String url) {
        LUIUtil.setProgressBarVisibility(progressBar, android.view.View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        tvProgress.setText("0%");
        bigImageView.showImage(Uri.parse(url));
    }

    public void load(String thumnail, String url) {
        LUIUtil.setProgressBarVisibility(progressBar, android.view.View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        tvProgress.setText("0%");
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

    public TextView getTvProgress() {
        return tvProgress;
    }

    public void setColorProgressBar(int color) {
        LUIUtil.setColorProgressBar(progressBar, color);
    }

    public void setColorProgressTextView(int color) {
        tvProgress.setTextColor(color);
    }
}
