package vn.loitp.views.imageview.bigimageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;

//https://github.com/Piasy/BigImageViewer
public class LBigImageView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private BigImageView bigImageView;
    //private TextView tvProgress;
    //private ProgressBar progressBar;

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
        //tvProgress = (TextView) findViewById(R.id.tv_progress);
        //LUIUtil.setTextShadow(tvProgress);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        bigImageView.setImageViewFactory(new GlideImageViewFactory());

        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
                //LLog.d(TAG, "onCacheHit");
                //LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
                //LLog.d(TAG, "onCacheMiss");
                //LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onStart() {
                //LLog.d(TAG, "onStart");
            }

            @Override
            public void onProgress(int progress) {
                //tvProgress.setText(progress + "");
            }

            @Override
            public void onFinish() {
                //LLog.d(TAG, "onFinish");
                //tvProgress.setVisibility(GONE);
                //LUIUtil.setProgressBarVisibility(progressBar, GONE);
            }

            @Override
            public void onSuccess(File image) {
                //LLog.d(TAG, "onSuccess");
                //tvProgress.setVisibility(GONE);
                //LUIUtil.setProgressBarVisibility(progressBar, GONE);
                if (isHaveThumbnail) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            LLog.d(TAG, "onSuccess if");
                            isHaveThumbnail = false;
                            load(urlMain);
                        }
                    });
                } else {
                    LLog.d(TAG, "onSuccess else");
                    if (callback != null) {
                        callback.onSuccess(image);
                    }
                }
            }

            @Override
            public void onFail(Exception error) {
                LLog.e(TAG, "onFail " + error);
                //tvProgress.setVisibility(VISIBLE);
                //LAnimationUtil.play(tvProgress, Techniques.Pulse);
                //tvProgress.setText("Error");
                //LUIUtil.setProgressBarVisibility(progressBar, GONE);
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

    /*public void setZoomEnable(boolean isEnable) {
        if (getSSIV() != null) {
            getSSIV().setZoomEnabled(isEnable);
        }
    }*/

    public SubsamplingScaleImageView getSSIV() {
        if (bigImageView.getSSIV() == null) {
            LLog.e(TAG, "getSSIV null");
        }
        return bigImageView.getSSIV();
    }

    public void clear() {
        if (bigImageView != null) {
            bigImageView.cancel();
        }
        if (getSSIV() != null) {
            getSSIV().recycle();
        }
    }


    public void load(String url) {
        //LUIUtil.setProgressBarVisibility(progressBar, android.view.View.VISIBLE);
        //tvProgress.setVisibility(View.VISIBLE);
        //tvProgress.setText("0%");
        if (url == null) {
            throw new NullPointerException("url null");
        }
        //LLog.d(TAG, "load url " + url);
        bigImageView.showImage(Uri.parse(url));
    }

    private boolean isHaveThumbnail;
    private String urlMain;

    public void load(String urlThumbnail, String urlMain) {
        //LUIUtil.setProgressBarVisibility(progressBar, android.view.View.VISIBLE);
        //tvProgress.setVisibility(View.VISIBLE);
        //tvProgress.setText("0%");
        if (urlThumbnail == null) {
            throw new NullPointerException("urlThumbnail null");
        }
        isHaveThumbnail = true;
        this.urlMain = urlMain;
        load(urlThumbnail);
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

    /*public void setColorProgressBar(int color) {
        LUIUtil.setColorProgressBar(progressBar, color);
    }

    public TextView getTvProgress() {
        return tvProgress;
    }

    public void setColorProgressTextView(int color) {
        tvProgress.setTextColor(color);
    }*/
}
