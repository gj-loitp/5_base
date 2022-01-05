package vn.loitp.app.activity.picker.crop

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LImageUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.picker.crop.CropImage
import com.picker.crop.LCropImageView
import com.picker.crop.LGalleryActivity
import com.views.setSafeOnClickListener
import com.yalantis.ucrop.util.FileUtils
import kotlinx.android.synthetic.main.activity_crop.*
import vn.loitp.app.R
import java.io.File

@LogTag("CropActivity")
@IsFullScreen(false)
class CropActivity : BaseFontActivity() {

    companion object {
        private const val REQUEST_CODE_GET_FILE = 1
    }

    private var isOvalOption = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_crop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btCropOval.setSafeOnClickListener {
            isOvalOption = true
            crop()
        }
        btCropSquare.setSafeOnClickListener {
            isOvalOption = false
            crop()
        }
    }

    private fun crop() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    startActivityForResult(
                        Intent(this@CropActivity, LGalleryActivity::class.java),
                        REQUEST_CODE_GET_FILE
                    )
                    LActivityUtil.tranIn(this@CropActivity)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    showShortInformation("Error onPermissionDenied WRITE_EXTERNAL_STORAGE", true)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    showShortInformation("Error onPermissionDenied WRITE_EXTERNAL_STORAGE", true)
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        logD("onActivityResult requestCode $requestCode")
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) {
            logD("data == null return")
            return
        }
        if (requestCode == REQUEST_CODE_GET_FILE) {
            if (data.extras == null) {
                logD("data.getExtras() == null return")
                return
            }
            val filePath = data.extras?.getString(LGalleryActivity.RETURN_VALUE)
            if (filePath == null) {
                logD("filePath == null return")
                return
            }
            logD("filePath $filePath")
            val file = File(filePath)
            if (!file.exists()) {
                logD("file is not exists")
                return
            }
            val imageUri = Uri.fromFile(file)
            if (imageUri == null) {
                logD("imageUri == null")
                return
            }
            if (isOvalOption) {
                CropImage.activity(imageUri)
                    .setGuidelines(LCropImageView.Guidelines.ON)
                    .setBorderLineColor(Color.WHITE)
                    .setBorderLineThickness(2f)
                    .setCircleSize(30f)
                    .setCropShape(LCropImageView.CropShape.OVAL)
                    .setCircleColor(Color.WHITE)
                    .setBackgroundColor(Color.argb(200, 0, 0, 0))
                    .start(this)
            } else {
                CropImage.activity(imageUri)
                    .setGuidelines(LCropImageView.Guidelines.OFF)
                    .setBorderLineColor(Color.RED)
                    .setBorderLineThickness(2f)
                    .setCircleSize(15f)
                    .setCropShape(LCropImageView.CropShape.RECTANGLE)
                    .setCircleColor(Color.RED)
                    .setBackgroundColor(Color.argb(200, 0, 0, 0))
                    .setAspectRatio(1, 1)
                    .setAutoZoomEnabled(true)
                    .setBorderCornerColor(Color.BLUE)
                    .setGuidelinesColor(Color.GREEN)
                    .start(this)
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (result != null) {
                val realPath = FileUtils.getPath(this, result.uri) ?: return
                val file = File(realPath)
                logD("onActivityResult file " + file.path)
                LImageUtil.load(
                    context = this,
                    any = file,
                    imageView = imageView,
                    resPlaceHolder = R.color.colorPrimary,
                    resError = R.color.colorPrimary,
                    transformation = null,
                    drawableRequestListener = null
                )
            }
        }
    }
}
