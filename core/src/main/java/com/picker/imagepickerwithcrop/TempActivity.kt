package com.picker.imagepickerwithcrop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.yalantis.ucrop.UCrop

@LogTag("TempActivity")
@IsFullScreen(false)
class TempActivity : BaseFontActivity() {

    private var pickerManager: PickerManager? = null

    override fun setLayoutResourceId(): Int {
        return Constants.NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickerManager = GlobalHolder.instance.pickerManager
        pickerManager?.let {
            it.setActivity(this)
            it.pickPhotoWithPermission()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            finish()
            return
        }
        when (requestCode) {
            PickerManager.REQUEST_CODE_SELECT_IMAGE -> {
                val uri = if (data != null) {
                    data.data
                } else {
                    pickerManager?.imageFile
                }
                pickerManager?.let {
                    it.setUri(uri)
                    it.startCropActivity()
                }
            }
            UCrop.REQUEST_CROP -> if (data != null) {
                pickerManager?.handleCropResult(data)
            } else {
                onBackPressed()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PickerManager.REQUEST_CODE_IMAGE_PERMISSION) {
            pickerManager?.handlePermissionResult(grantResults)
        } else {
            onBackPressed()
        }
    }
}
