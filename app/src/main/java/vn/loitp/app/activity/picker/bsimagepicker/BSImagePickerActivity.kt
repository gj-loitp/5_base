package vn.loitp.app.activity.picker.bsimagepicker

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.widget.ImageView
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.asksira.bsimagepicker.BSImagePicker
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LPickerUtil
import kotlinx.android.synthetic.main.activity_picker_bs_image.*
import vn.loitp.app.R

//https://github.com/siralam/BSImagePicker

@LayoutId(R.layout.activity_picker_bs_image)
@LogTag("BSImagePickerActivity")
@IsFullScreen(false)
class BSImagePickerActivity : BaseFontActivity(), BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnSelectImageCancelledListener {
    private val providerAuthority = ".activity.picker.bsimagepicker.BSImagePickerActivity.fileprovider"
    private var isCropEnable = false

    companion object {
        private const val CROP_PIC_REQUEST_CODE = 56789
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        tvSingleSelection.setOnClickListener {
            isCropEnable = false
            val pickerDialog = BSImagePicker.Builder(providerAuthority).build()
            pickerDialog.show(supportFragmentManager, "picker")
        }

        tvMultiSelection.setOnClickListener {
            val pickerDialog = BSImagePicker.Builder(providerAuthority)
                    .setMaximumDisplayingImages(Int.MAX_VALUE)
                    .isMultiSelect
                    .setMinimumMultiSelectCount(3)
                    .setMaximumMultiSelectCount(6)
                    .build()
            pickerDialog.show(supportFragmentManager, "picker")
        }

        tvSingleSelectionCrop.setOnClickListener {
            isCropEnable = true
            val pickerDialog = BSImagePicker.Builder(providerAuthority).build()
            pickerDialog.show(supportFragmentManager, "picker")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CROP_PIC_REQUEST_CODE) {
            logD("onActivityResult CROP_PIC_REQUEST_CODE")
            if (data != null) {
                val extras = data.extras ?: return
                val bitmap = extras.getParcelable<Bitmap>("data")
                ivImage1.setImageBitmap(bitmap)
            }
        }
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        logD("onSingleImageSelected $uri, $tag")
        showShortInformation("onSingleImageSelected $uri, $tag")
        if (isCropEnable) {
            val aspectX = 1
            val aspectY = 1
            val outputX = 500
            val outputY = 500
            LPickerUtil.cropImage(activity = this, picUri = uri, CROP_PIC_REQUEST_CODE = CROP_PIC_REQUEST_CODE, aspectX = aspectX, aspectY = aspectY, outputX = outputX, outputY = outputY)
        } else {
            LImageUtil.load(context = this, any = uri, imageView = ivImage2)
        }
    }

    override fun onMultiImageSelected(uriList: List<Uri>?, tag: String?) {
        if (uriList == null || tag == null) {
            return
        }
        for (i in uriList.indices) {
            if (i >= 6) return
            val iv: ImageView? = when (i) {
                0 -> ivImage1
                1 -> ivImage2
                2 -> ivImage3
                3 -> ivImage4
                4 -> ivImage5
                5 -> ivImage6
                else -> ivImage6
            }
            LImageUtil.load(context = this, any = uriList[i], imageView = iv)
        }
    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView?) {
        logD("loadImage imageUri $imageUri")
        LImageUtil.load(context = this, any = imageUri, imageView = ivImage)
    }

    override fun onCancelled(isMultiSelecting: Boolean, tag: String?) {
        showShortInformation("Selection is cancelled, Multi-selection is $isMultiSelecting")
    }
}

