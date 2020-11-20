package vn.loitp.app.activity.picker.imagepickerwthcop

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.core.utilities.LDialogUtil.Companion.showDialog2
import com.interfaces.Callback2
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.picker.imagepickerwithcrop.PickerBuilder
import com.utils.util.AppUtils
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_image_picker_with_crop.*
import vn.loitp.app.R

@LogTag("ImageWithCropActivity")
@IsFullScreen(false)
class ImageWithCropActivity : BaseFontActivity() {

    private var isShowDialogCheck = false
    private var name: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_image_picker_with_crop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        name = AppUtils.getAppName()
        //check permission
        checkPermission()
        startGalleryBtn.setSafeOnClickListener {
            PickerBuilder(this, PickerBuilder.SELECT_FROM_GALLERY)
                    .setOnImageReceivedListener { imageUri: Uri ->
                        imageView.setImageURI(imageUri)
                        showShortInformation("Got image - $imageUri", true)
                    }
                    .setImageName(name + System.currentTimeMillis())
                    .setImageFolderName(name)
                    .setCropScreenColor(LAppResource.getColor(R.color.colorPrimary))
                    .setOnPermissionRefusedListener {}
                    .start()
        }
        startCameraBtn.setOnClickListener {
            PickerBuilder(this, PickerBuilder.SELECT_FROM_CAMERA)
                    .setOnImageReceivedListener { imageUri: Uri ->
                        imageView.setImageURI(imageUri)
                        showShortInformation("Got image - $imageUri", true)
                    }
                    .setImageName(name + System.currentTimeMillis())
                    .setImageFolderName(name)
                    .withTimeStamp(false)
                    .setCropScreenColor(LAppResource.getColor(R.color.colorPrimary))
                    .start()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    private fun checkPermission() {
        isShowDialogCheck = true
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            logD("onPermissionsChecked do you work now")
                        } else {
                            logD("!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            logD("onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        logD("onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature.",
                button1 = "Okay",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        checkPermission()
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature. You can grant them in app settings.",
                button1 = "GOTO SETTINGS",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        isShowDialogCheck = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 101)
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }
}
