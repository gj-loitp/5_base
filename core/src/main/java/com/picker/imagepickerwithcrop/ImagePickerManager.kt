package com.picker.imagepickerwithcrop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class ImagePickerManager(
        activity: Activity
) : PickerManager(activity) {

    override fun sendToExternalApp() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        activity.startActivityForResult(Intent.createChooser(intent, "Select avatar..."), REQUEST_CODE_SELECT_IMAGE)
    }

    override fun setUri(uri: Uri?) {
        super.setUri(uri)

        mProcessingPhotoUri = uri
    }
}
