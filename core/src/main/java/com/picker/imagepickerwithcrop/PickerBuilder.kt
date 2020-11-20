package com.picker.imagepickerwithcrop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.yalantis.ucrop.UCrop

class PickerBuilder(
        private val activity: Activity,
        type: Int
) {

    companion object {
        const val SELECT_FROM_GALLERY = 0
        const val SELECT_FROM_CAMERA = 1
    }

    private val permissionRefusedListener: OnPermissionRefusedListener? = null
    private var imageReceivedListener: OnImageReceivedListener? = null
    private val pickerManager: PickerManager

    interface OnPermissionRefusedListener {
        fun onPermissionRefused()
    }

    interface OnImageReceivedListener {
        fun onImageReceived(imageUri: Uri?)
    }

    fun start() {
        val intent = Intent(activity, TempActivity::class.java)
        activity.startActivity(intent)
        GlobalHolder.instance.pickerManager = pickerManager
    }

    fun setOnImageReceivedListener(listener: OnImageReceivedListener?): PickerBuilder {
        pickerManager.setOnImageReceivedListener(listener)
        return this
    }

    fun setOnPermissionRefusedListener(listener: OnPermissionRefusedListener?): PickerBuilder {
        pickerManager.setOnPermissionRefusedListener(listener)
        return this
    }

    fun setCropScreenColor(cropScreenColor: Int): PickerBuilder {
        pickerManager.setCropActivityColor(cropScreenColor)
        return this
    }

    fun setImageName(imageName: String?): PickerBuilder {
        imageName?.let {
            pickerManager.setImageName(it)
        }
        return this
    }

    fun withTimeStamp(withTimeStamp: Boolean): PickerBuilder {
        pickerManager.withTimeStamp(withTimeStamp)
        return this
    }

    fun setImageFolderName(folderName: String?): PickerBuilder {
        pickerManager.setImageFolderName(folderName)
        return this
    }

    fun setCustomizedUcrop(ucrop: UCrop?): PickerBuilder {
        pickerManager.setCustomizedUcrop(ucrop)
        return this
    }

    init {
        pickerManager = if (type == SELECT_FROM_GALLERY) {
            ImagePickerManager(activity)
        } else {
            CameraPickerManager(activity)
        }
    }
}
