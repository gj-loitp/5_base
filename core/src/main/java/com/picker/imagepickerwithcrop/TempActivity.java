package com.picker.imagepickerwithcrop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;

import static com.yalantis.ucrop.UCrop.REQUEST_CROP;

@LogTag("TempActivity")
@IsFullScreen(false)
public class TempActivity extends BaseFontActivity {
    private PickerManager pickerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(0);
        this.pickerManager = GlobalHolder.getInstance().getPickerManager();
        this.pickerManager.setActivity(this);
        this.pickerManager.pickPhotoWithPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        switch (requestCode) {
            case PickerManager.REQUEST_CODE_SELECT_IMAGE:
                final Uri uri;
                if (data != null) {
                    uri = data.getData();
                } else {
                    uri = pickerManager.getImageFile();
                }

                pickerManager.setUri(uri);
                pickerManager.startCropActivity();
                break;
            case REQUEST_CROP:
                if (data != null) {
                    pickerManager.handleCropResult(data);
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PickerManager.REQUEST_CODE_IMAGE_PERMISSION) {
            pickerManager.handlePermissionResult(grantResults);
        } else {
            finish();
        }

    }

}
