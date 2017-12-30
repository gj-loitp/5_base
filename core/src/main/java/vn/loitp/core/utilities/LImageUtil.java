package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import loitp.core.R;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 10/7/2017.
 */

public class LImageUtil {
    //for flide
    public static void load(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView, final AVLoadingIndicatorView avLoadingIndicatorView) {
        Glide.with(activity).load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            avLoadingIndicatorView.smoothToHide();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            avLoadingIndicatorView.smoothToHide();
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView, int sizeW, int sizeH) {
        Glide.with(activity).load(url).override(sizeW, sizeH).into(imageView);
    }

    //for SimpleDraweeView

    public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
        if (!android.text.TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            simpleDraweeView.setImageURI(uri);
        }
    }

    /*public static void loadAvatar(SimpleDraweeView simpleDraweeView, long id) {
        Context context = simpleDraweeView.getContext();
        String webServiceURL = context.getString(R.string.webService_URL);
        Uri uri = Uri.parse(context.getString(R.string.format_avatar, webServiceURL, id));
        simpleDraweeView.setImageURI(uri);
    }*/

    public static void loadImage(SimpleDraweeView simpleDraweeView, int resId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        simpleDraweeView.setImageURI(uri);
    }

    public static void loadImage(SimpleDraweeView simpleDraweeView, Uri uri) {
        simpleDraweeView.setImageURI(uri);
    }

    public static void loadBitmap(Context context, String url, int width, int height, final OnLoadCompletedListener onLoadCompletedListener) {
        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableBitmap>>() {
            @Override
            public void onNewResultImpl(
                    DataSource<CloseableReference<CloseableBitmap>> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }
                try {
                    CloseableReference<CloseableBitmap> imageReference = dataSource.getResult();
                    if (imageReference != null) {
                        final CloseableReference<CloseableBitmap> closeableReference = imageReference.clone();
                        try {
                            CloseableBitmap closeableBitmap = closeableReference.get();
                            Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();
                            if (bitmap != null && !bitmap.isRecycled()) {
                                onLoadCompletedListener.onLoadCompleted(bitmap);
                            }
                        } finally {
                            imageReference.close();
                            closeableReference.close();
                        }
                    }
                } finally {
                    dataSource.close();
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                // Throwable throwable = dataSource.getFailureCause();
                // handle failure
            }
        };

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url));
        if (width > 0 && height > 0) {
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.build();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, context);
        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }

    public interface OnLoadCompletedListener {
        void onLoadCompleted(Bitmap bitmap);
    }
}
