package vn.loitp.app.activity.picker.bsimagepicker;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/siralam/BSImagePicker
public class BSImagePickerActivity extends BaseFontActivity implements BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.OnMultiImageSelectedListener {
    private ImageView ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6;
    private final String providerAuthority = "vn.loitp.app.activity.picker.bsimagepicker.BSImagePickerActivity.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivImage1 = findViewById(R.id.iv_image1);
        ivImage2 = findViewById(R.id.iv_image2);
        ivImage3 = findViewById(R.id.iv_image3);
        ivImage4 = findViewById(R.id.iv_image4);
        ivImage5 = findViewById(R.id.iv_image5);
        ivImage6 = findViewById(R.id.iv_image6);
        findViewById(R.id.tv_single_selection).setOnClickListener(v -> {
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
        return R.layout.activity_bs_image_picker;
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        Glide.with(activity).load(uri).into(ivImage2);
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
            Glide.with(this).load(uriList.get(i)).into(iv);
        }
    }
}
