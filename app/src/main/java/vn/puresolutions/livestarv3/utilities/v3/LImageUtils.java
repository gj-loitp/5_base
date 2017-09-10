package vn.puresolutions.livestarv3.utilities.v3;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import vn.puresolutions.livestar.R;

import static com.facebook.FacebookSdk.getCacheDir;

public class LImageUtils {
    private final static String TAG = LImageUtils.class.getSimpleName();

    public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
        if (!android.text.TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            simpleDraweeView.setImageURI(uri);
        }
    }

    public static void loadAvatar(SimpleDraweeView simpleDraweeView, long id) {
        Context context = simpleDraweeView.getContext();
        String webServiceURL = context.getString(R.string.webService_URL);
        Uri uri = Uri.parse(context.getString(R.string.format_avatar, webServiceURL, id));
        simpleDraweeView.setImageURI(uri);
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

    public static void openAndCropImage(Activity activity, int ratioWidth, int ratioHeght) {
        if (activity == null || ratioWidth <= 0 || ratioHeght <= 0) {
            return;
        }
        CropImage.activity()
                .setAspectRatio(ratioWidth, ratioHeght)
                //.setBackgroundColor(Color.RED)
                .setAutoZoomEnabled(true)
                //.setCropShape(CropImageView.CropShape.RECTANGLE)
                //.setMinCropResultSize(300,300)
                //.setMaxCropResultSize(1080,1080)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static Bitmap decodeUriAsBitmap(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static String getPathFromURI(Activity activity, Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return picturePath;
        }
        return null;
    }

    public static Uri saveCroppedImage(Activity activity, Bitmap croppedImage) {
        Uri saveUri = null;
        File file = new File(getCacheDir(), "cropped");
        OutputStream outputStream = null;
        try {
            file.getParentFile().mkdirs();
            saveUri = Uri.fromFile(file);
            outputStream = activity.getContentResolver().openOutputStream(saveUri);
            if (outputStream != null) {
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveUri;
    }
}
