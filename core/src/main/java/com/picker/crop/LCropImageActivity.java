// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.picker.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.R;
import com.core.base.BaseFontActivity;

import java.io.File;
import java.io.IOException;

/**
 * Built-in activity for image cropping.<br>
 */
public class LCropImageActivity extends BaseFontActivity implements LCropImageView.OnSetImageUriCompleteListener, LCropImageView.OnCropImageCompleteListener,
        View.OnClickListener {

    /**
     * The crop image view library widget used in the activity
     */
    //private CropImageView cropImageView;

    /**
     * the options that were set for the crop image
     */
    private CropImageOptions mOptions;

    //private LinearLayout ll_layout;
    //private RelativeLayout ll_top_btns;
    //private ImageButton btn_rotaion, btn_crop, btn_cancel, btn_cut_confirm;


    private LCropImageView LCropImageView;
    private LinearLayout ll_layout;
    private ImageButton btn_rotaion;
    private ImageButton btn_close;
    private ImageButton btn_crop;
    private ImageButton btn_cancel;
    private ImageButton btn_cut_confirm;
    private ImageButton btn_confirm;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeSystembarColor();
        LCropImageView = findViewById(R.id.cropImageView);
        ll_layout = findViewById(R.id.ll_layout);
        btn_rotaion = findViewById(R.id.btn_rotaion);
        btn_close = findViewById(R.id.btn_close);
        btn_crop = findViewById(R.id.btn_crop);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cut_confirm = findViewById(R.id.btn_cut_confirm);
        btn_confirm = findViewById(R.id.btn_confirm);
        LCropImageView.setShowCropOverlay(false);

        final Intent intent = getIntent();
        final Uri source = intent.getParcelableExtra(CropImage.CROP_IMAGE_EXTRA_SOURCE);
        mOptions = intent.getParcelableExtra(CropImage.CROP_IMAGE_EXTRA_OPTIONS);
        if (savedInstanceState == null) {
            LCropImageView.setImageUriAsync(source);
        }
        setEvents();
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
        return R.layout.l_activity_l_crop_image;
    }

    private void setEvents() {
        btn_rotaion.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_crop.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_cut_confirm.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    private void changeSystembarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#F4B04F"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LCropImageView.setOnSetImageUriCompleteListener(this);
        LCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        LCropImageView.setOnSetImageUriCompleteListener(null);
        LCropImageView.setOnCropImageCompleteListener(null);
    }

    private void setShowCropper(final boolean showCropOverlay) {
        if (showCropOverlay) {
            ll_layout.setBackgroundColor(Color.BLACK);
            btn_rotaion.setVisibility(View.GONE);
            btn_crop.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
            btn_cut_confirm.setVisibility(View.VISIBLE);
        } else {
            ll_layout.setBackgroundColor(Color.WHITE);
            btn_rotaion.setVisibility(View.VISIBLE);
            btn_crop.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);
            btn_cut_confirm.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override
    public void onSetImageUriComplete(LCropImageView view, Uri uri, Exception error) {
        if (error == null) {
            if (mOptions.initialCropWindowRectangle != null) {
                LCropImageView.setCropRect(mOptions.initialCropWindowRectangle);
            }
            if (mOptions.initialRotation > -1) {
                LCropImageView.setRotatedDegrees(mOptions.initialRotation);
            }
        } else {
            setResult(null, error, 1);
        }
    }

    @Override
    public void onCropImageComplete(LCropImageView view, LCropImageView.CropResult result) {
        if (LCropImageView.isShowCropOverlay()) {
            Uri outputUri = result.getUri();
            LCropImageView.setImageUriAsync(outputUri);
            LCropImageView.setShowCropOverlay(false);
            setShowCropper(false);
        } else setResult(result.getUri(), result.getError(), result.getSampleSize());
    }

    //region: Private methods

    /**
     * Execute crop image and save the result tou output uri.
     */
    protected void cropImage() {
        if (mOptions.noOutputImage) {
            setResult(null, null, 1);
        } else {
            Uri outputUri = getOutputUri();
            LCropImageView.saveCroppedImageAsync(outputUri,
                    mOptions.outputCompressFormat,
                    mOptions.outputCompressQuality,
                    mOptions.outputRequestWidth,
                    mOptions.outputRequestHeight,
                    mOptions.outputRequestSizeOptions);
        }
    }

    /**
     * Rotate the image in the crop image view.
     */
    protected void rotateImage(int degrees) {
        LCropImageView.rotateImage(degrees);
    }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.outputUri;
        if (outputUri.equals(Uri.EMPTY)) {
            try {
                final String ext = mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG ? ".jpg" :
                        mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    /**
     * Result with cropped image data or error if failed.
     */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        final int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();
    }

    /**
     * Cancel of cropping activity.
     */
    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * Get intent instance to be used for the result of this activity.
     */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        final CropImage.ActivityResult result = new CropImage.ActivityResult(null,
                uri,
                error,
                LCropImageView.getCropPoints(),
                LCropImageView.getCropRect(),
                LCropImageView.getRotatedDegrees(),
                sampleSize);
        final Intent intent = new Intent();
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }

    /**
     * Update the color of a specific menu item to the given color.
     */
    private void updateMenuItemIconColor(Menu menu, int itemId, int color) {
        final MenuItem menuItem = menu.findItem(itemId);
        if (menuItem != null) {
            final Drawable menuItemIcon = menuItem.getIcon();
            if (menuItemIcon != null) {
                try {
                    menuItemIcon.mutate();
                    menuItemIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    menuItem.setIcon(menuItemIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_close) {
            onBackPressed();
        } else if (view.getId() == R.id.btn_confirm) {
            cropImage();
        } else if (view.getId() == R.id.btn_cut_confirm) {
            cropImage();
        } else if (view.getId() == R.id.btn_rotaion) {
            rotateImage(mOptions.rotationDegrees);
        } else if (view.getId() == R.id.btn_crop) {
            LCropImageView.setShowCropOverlay(true);
            setShowCropper(true);
        } else if (view.getId() == R.id.btn_cancel) {
            LCropImageView.setShowCropOverlay(false);
            setShowCropper(false);
        }
    }
    //endregion
}

