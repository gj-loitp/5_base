package vn.loitp.app.activity.picker.imagepickerwthcop;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.picker.imagepickerwithcrop.PickerBuilder;
import vn.loitp.views.LToast;

public class ImageWithCropActivity extends BaseFontActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.imageView);
        (findViewById(R.id.startCameraBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerBuilder(activity, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                LToast.showShort(activity, "Got image - " + imageUri, R.drawable.bkg_horizontal);
                                imageView.setImageURI(imageUri);
                            }
                        })
                        .setImageName("testImage")
                        .setImageFolderName("testFolder")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();
            }
        });

        (findViewById(R.id.startGalleryBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerBuilder(activity, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                imageView.setImageURI(imageUri);
                                LToast.showShort(activity, "Got image - " + imageUri, R.drawable.bkg_horizontal);
                            }
                        })
                        .setImageName("test")
                        .setImageFolderName("testFolder")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {
                            }
                        })
                        .start();
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_image_picker_with_crop;
    }

}
