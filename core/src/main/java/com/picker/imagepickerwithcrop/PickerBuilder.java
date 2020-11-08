package com.picker.imagepickerwithcrop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.yalantis.ucrop.UCrop;

/**
 * Created by Mickael on 13/10/2016.
 */

//TODO convert kotlin
public class PickerBuilder {
    public static final int SELECT_FROM_GALLERY = 0;
    public static final int SELECT_FROM_CAMERA = 1;
    private Activity activity;
    private onPermissionRefusedListener permissionRefusedListener;
    protected onImageReceivedListener imageReceivedListener;
    private PickerManager pickerManager;

    public PickerBuilder(final Activity activity, final int type) {
        this.activity = activity;
        pickerManager = (type == PickerBuilder.SELECT_FROM_GALLERY) ? new ImagePickerManager(activity) : new CameraPickerManager(activity);
    }

    public interface onPermissionRefusedListener {
        void onPermissionRefused();
    }

    public interface onImageReceivedListener {
        void onImageReceived(Uri imageUri);
    }


    public void start() {
        final Intent intent = new Intent(activity, TempActivity.class);
        activity.startActivity(intent);

        GlobalHolder.getInstance().setPickerManager(pickerManager);

    }

    public PickerBuilder setOnImageReceivedListener(final PickerBuilder.onImageReceivedListener listener) {
        pickerManager.setOnImageReceivedListener(listener);
        return this;
    }

    public PickerBuilder setOnPermissionRefusedListener(final PickerBuilder.onPermissionRefusedListener listener) {
        pickerManager.setOnPermissionRefusedListener(listener);
        return this;
    }

    public PickerBuilder setCropScreenColor(final int cropScreenColor) {
        pickerManager.setCropActivityColor(cropScreenColor);
        return this;
    }

    public PickerBuilder setImageName(final String imageName) {
        pickerManager.setImageName(imageName);
        return this;
    }

    public PickerBuilder withTimeStamp(final boolean withTimeStamp) {
        pickerManager.withTimeStamp(withTimeStamp);
        return this;
    }

    public PickerBuilder setImageFolderName(final String folderName) {
        pickerManager.setImageFolderName(folderName);
        return this;
    }

    public PickerBuilder setCustomizedUcrop(final UCrop ucrop) {
        pickerManager.setCustomizedUcrop(ucrop);
        return this;
    }

}
