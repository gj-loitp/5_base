package vn.loitp.core.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
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

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by www.muathu@gmail.com on 10/7/2017.
 */

public class LImageUtil {
    //for flide
    public static void clear(Context context, View target) {
        Glide.with(context).clear(target);
    }

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, RequestListener<Drawable> drawableRequestListener) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int resPlaceHolder) {
        //Glide.with(activity).load(url).placeholder(resPlaceHolder).into(imageView);
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView);
    }

    /*public static void load(Activity activity, String url, ImageView imageView, RequestListener<String, GlideDrawable> glideDrawableRequestListener) {
        Glide.with(activity).load(url)
                .listener(glideDrawableRequestListener)
                .into(imageView);
    }*/

    /*public static void loadNoEffect(Activity activity, String url, String oldImage, ImageView imageView) {
        Glide.with(activity).load(url)
                .thumbnail(
                        Glide // this thumbnail request has to have the same RESULT cache key
                        .with(activity) // as the outer request, which usually simply means
                        .load(oldImage) // same size/transformation(e.g. centerCrop)/format(e.g. asBitmap)
                        .centerCrop() // have to be explicit here to match outer load exactly
                )
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (isFirstResource) {
                            return false; // thumbnail was not shown, do as usual
                        }
                        return new DrawableCrossFadeFactory<Drawable>(customize animation here)
                                .build(false, false) // force crossFade() even if coming from memory cache
                                .animate(resource, (GlideAnimation.ViewAdapter) target);
                    }
                })
                .dontAnimate()
                .dontTransform()
                .into(imageView);
    }*/

    public static void load(Context context, String url, ImageView imageView, int resPlaceHolder, int resError, RequestListener<Drawable> drawableRequestListener) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, final AVLoadingIndicatorView avLoadingIndicatorView, int resPlaceHolder, int resError) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            //avLoadingIndicatorView.smoothToHide();
                            avLoadingIndicatorView.hide();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            //avLoadingIndicatorView.smoothToHide();
                            avLoadingIndicatorView.hide();
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, final ProgressBar progressBar, int sizeW, int sizeH) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(sizeW, sizeH)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, final ProgressBar progressBar) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, final AVLoadingIndicatorView avLoadingIndicatorView, int resPlaceHolder) {
        load(context, url, imageView, avLoadingIndicatorView, resPlaceHolder, Color.TRANSPARENT);
    }

    public static void load(Context context, String url, ImageView imageView, final AVLoadingIndicatorView avLoadingIndicatorView) {
        load(context, url, imageView, avLoadingIndicatorView, Color.TRANSPARENT, Color.TRANSPARENT);
    }

    public static void load(Context context, String url, ImageView imageView, int sizeW, int sizeH) {
        if (context == null) {
            return;
        }
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.trans)
                        //.fitCenter()
                        .override(sizeW, sizeH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                ).into(imageView);
    }

    public static void loadNoAmin(Context context, String url, ImageView imageView, RequestListener<Drawable> drawableRequestListener) {
        if (context == null) {
            return;
        }
        Glide.with(context).load(url)
                //.transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.trans)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .dontTransform()
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    //for SimpleDraweeView
    public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
        if (!android.text.TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            simpleDraweeView.setImageURI(uri);
        }
    }

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

    //for flick api url_m -> url_b
    public static String getFlickrLink100(String linkUrlM) {
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM == null || linkUrlM.isEmpty()) {
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_t.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_t.png");
        }
        return linkUrlM;
    }

    //for flick api url_m -> url_b
    public static String getFlickrLink640(String linkUrlM) {
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM == null || linkUrlM.isEmpty()) {
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_z.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_z.png");
        }
        return linkUrlM;
    }
    //for flick api url_m -> url_n
    public static String getFlickrLink320(String linkUrlM) {
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM == null || linkUrlM.isEmpty()) {
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_n.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_n.png");
        }
        return linkUrlM;
    }

    //for flick api url_m -> url_b
    public static String getFlickrLink1024(String linkUrlM) {
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM == null || linkUrlM.isEmpty()) {
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_b.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_b.png");
        }
        return linkUrlM;
    }
}
