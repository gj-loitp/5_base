package com.picker.imagepickerwithcrop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by Mickael on 10/10/2016.
 */

public class CameraPickerManager extends PickerManager {

    CameraPickerManager(Activity activity) {
        super(activity);
    }

    protected void sendToExternalApp() {
        final ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        mProcessingPhotoUri = activity.getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mProcessingPhotoUri);
        activity.startActivityForResult(captureIntent, REQUEST_CODE_SELECT_IMAGE);
    }
}
