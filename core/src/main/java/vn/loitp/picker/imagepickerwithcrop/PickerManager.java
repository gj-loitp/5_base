package vn.loitp.picker.imagepickerwithcrop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import loitp.core.R;

/**
 * Created by Mickael on 10/10/2016.
 */

public abstract class PickerManager {
    public static final int REQUEST_CODE_SELECT_IMAGE = 200;
    public static final int REQUEST_CODE_IMAGE_PERMISSION = 201;
    protected Uri mProcessingPhotoUri;
    private boolean withTimeStamp = true;
    private String folder = null;
    private String imageName;
    protected Activity activity;
    private UCrop uCrop;
    protected PickerBuilder.onImageReceivedListener imageReceivedListener;
    protected PickerBuilder.onPermissionRefusedListener permissionRefusedListener;
    private int cropActivityColor = R.color.colorPrimary;

    public PickerManager setOnImageReceivedListener(final PickerBuilder.onImageReceivedListener listener) {
        this.imageReceivedListener = listener;
        return this;
    }

    public PickerManager setOnPermissionRefusedListener(final PickerBuilder.onPermissionRefusedListener listener) {
        this.permissionRefusedListener = listener;
        return this;
    }

    public PickerManager(final Activity activity) {
        this.activity = activity;
        this.imageName = activity.getString(R.string.app_name);
    }


    public void pickPhotoWithPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_IMAGE_PERMISSION);
        } else {
            sendToExternalApp();
        }
    }

    public void handlePermissionResult(final int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted
            sendToExternalApp();
        } else {
            // permission denied
            if (permissionRefusedListener != null) {
                permissionRefusedListener.onPermissionRefused();
            }
            activity.finish();
        }
    }

    protected abstract void sendToExternalApp();

    protected Uri getImageFile() {
        final String imagePathStr = Environment.getExternalStorageDirectory() + "/" +
                (folder == null ?
                        Environment.DIRECTORY_DCIM + "/" + activity.getString(R.string.app_name) :
                        folder);

        final File path = new File(imagePathStr);
        if (!path.exists()) {
            path.mkdir();
        }

        final String finalPhotoName = imageName +
                (withTimeStamp ? "_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date(System.currentTimeMillis())) : "")
                + ".jpg";

        // long currentTimeMillis = System.currentTimeMillis();
        // String photoName = imageName + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date(currentTimeMillis)) + ".jpg";
        final File photo = new File(path, finalPhotoName);
        return Uri.fromFile(photo);
    }

    public void setUri(final Uri uri) {

    }

    @SuppressLint("ResourceAsColor")
    public void startCropActivity() {
        if (uCrop == null) {
            uCrop = UCrop.of(mProcessingPhotoUri, getImageFile());
            uCrop = uCrop.useSourceImageAspectRatio();
            final UCrop.Options options = new UCrop.Options();
            options.setFreeStyleCropEnabled(true);

            options.setToolbarColor(cropActivityColor);
            options.setStatusBarColor(cropActivityColor);
            options.setActiveWidgetColor(cropActivityColor);
            uCrop = uCrop.withOptions(options);
        }

        uCrop.start(activity);
    }

    public void handleCropResult(final Intent data) {
        final Uri resultUri = UCrop.getOutput(data);
        if (imageReceivedListener != null) {
            imageReceivedListener.onImageReceived(resultUri);
        }
        activity.finish();
    }


    public PickerManager setActivity(final Activity activity) {
        this.activity = activity;
        return this;
    }

    public PickerManager setImageName(final String imageName) {
        this.imageName = imageName;
        return this;
    }

    public PickerManager setCropActivityColor(final int cropActivityColor) {
        this.cropActivityColor = cropActivityColor;
        return this;
    }

    public PickerManager withTimeStamp(final boolean withTimeStamp) {
        this.withTimeStamp = withTimeStamp;
        return this;
    }

    public PickerManager setImageFolderName(final String folder) {
        this.folder = folder;
        return this;
    }

    public PickerManager setCustomizedUcrop(final UCrop customizedUcrop) {
        this.uCrop = customizedUcrop;
        return this;
    }
}
