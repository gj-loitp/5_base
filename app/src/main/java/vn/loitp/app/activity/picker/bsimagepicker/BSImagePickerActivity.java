package vn.loitp.app.activity.picker.bsimagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import com.asksira.bsimagepicker.BSImagePicker;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPickerUtil;

//https://github.com/siralam/BSImagePicker
public class BSImagePickerActivity extends BaseFontActivity implements BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.OnMultiImageSelectedListener {
    private ImageView ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6;
    private final String providerAuthority = "vn.loitp.app.activity.function.bsimagepicker.BSImagePickerActivity.fileprovider";
    private boolean isCropEnable;
    private final int CROP_PIC_REQUEST_CODE = 56789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ivImage1 = findViewById(R.id.iv_image1);
        ivImage2 = findViewById(R.id.iv_image2);
        ivImage3 = findViewById(R.id.iv_image3);
        ivImage4 = findViewById(R.id.iv_image4);
        ivImage5 = findViewById(R.id.iv_image5);
        ivImage6 = findViewById(R.id.iv_image6);

        findViewById(R.id.tv_single_selection).setOnClickListener(v -> {
            isCropEnable = false;
            final BSImagePicker pickerDialog = new BSImagePicker.Builder(providerAuthority).build();
            pickerDialog.show(getSupportFragmentManager(), "picker");
        });
        findViewById(R.id.tv_multi_selection).setOnClickListener(v -> {
            final BSImagePicker pickerDialog = new BSImagePicker.Builder(providerAuthority)
                    .setMaximumDisplayingImages(Integer.MAX_VALUE)
                    .isMultiSelect()
                    .setMinimumMultiSelectCount(3)
                    .setMaximumMultiSelectCount(6)
                    .build();
            pickerDialog.show(getSupportFragmentManager(), "picker");
        });
        findViewById(R.id.tv_single_selection_crop).setOnClickListener(v -> {
            isCropEnable = true;
            final BSImagePicker pickerDialog = new BSImagePicker.Builder(providerAuthority).build();
            pickerDialog.show(getSupportFragmentManager(), "picker");
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_bs_image_picker;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_PIC_REQUEST_CODE) {
            LLog.d(TAG, "onActivityResult CROP_PIC_REQUEST_CODE");
            if (data != null) {
                final Bundle extras = data.getExtras();
                if (extras == null) {
                    return;
                }
                final Bitmap bitmap = extras.getParcelable("data");
                ivImage1.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        if (isCropEnable) {
            final int aspectX = 1;
            final int aspectY = 1;
            final int outputX = 500;
            final int outputY = 500;
            LPickerUtil.INSTANCE.cropImage(activity, uri, CROP_PIC_REQUEST_CODE, aspectX, aspectY, outputX, outputY);
        } else {
            LImageUtil.load(activity, uri, ivImage2);
        }
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        for (int i = 0; i < uriList.size(); i++) {
            if (i >= 6) return;
            final ImageView iv;
            switch (i) {
                case 0:
                    iv = ivImage1;
                    break;
                case 1:
                    iv = ivImage2;
                    break;
                case 2:
                    iv = ivImage3;
                    break;
                case 3:
                    iv = ivImage4;
                    break;
                case 4:
                    iv = ivImage5;
                    break;
                case 5:
                default:
                    iv = ivImage6;
            }
            LImageUtil.load(activity, uriList.get(i), iv);
        }
    }
}
