package vn.loitp.app.activity.picker.scopedMediaPicker

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.media.scopemediapicker.ScopedMediaPicker
import kotlinx.android.synthetic.main.activity_scoped_media_picker.*
import vn.loitp.app.R

// TODO check this libary https://github.com/bhoomit11/ScopedMediaPicker/issues/2
@LogTag("ScopedMediaPickerActivity")
@IsFullScreen(false)
class ScopedMediaPickerActivity : BaseFontActivity() {

    private val scopedMediaPicker by lazy {
        ScopedMediaPicker(
            activity = this@ScopedMediaPickerActivity,
            requiresCrop = true,
            allowMultipleFiles = true
        )
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scoped_media_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/bhoomit11/ScopedMediaPicker"
                        )
                    }
                )
                it.isVisible = true
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ScopedMediaPickerActivity::class.java.simpleName
        }
        btnCapture.setOnClickListener {
            pickCapture()
        }
        btnFile.setOnClickListener {
            pickFile()
        }
    }

    private fun pickCapture() {
        // CHON HINH VA VIDEO CUNG LUC
        scopedMediaPicker.startMediaPicker(
            mediaType = ScopedMediaPicker.MEDIA_TYPE_IMAGE
                    or ScopedMediaPicker.MEDIA_TYPE_VIDEO,
            actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY
        ) { pathList, type ->
            when (type) {
                ScopedMediaPicker.MEDIA_TYPE_IMAGE -> {
                    logD(">>>MEDIA_TYPE_IMAGE")
                }
                ScopedMediaPicker.MEDIA_TYPE_VIDEO -> {
                    logD(">>>MEDIA_TYPE_VIDEO")
                }
            }
            var s = ""
            pathList.forEach {
                s += it + "\n"
            }
            tvPath.text = s
        }

        // CHON HINH TU GALLERY
//            scopedMediaPicker.startMediaPicker(
//                mediaType = ScopedMediaPicker.MEDIA_TYPE_IMAGE,
//                actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY
//            ) { pathList, _ ->
//                var s = ""
//                pathList.forEach {
//                    s += it + "\n"
//                }
//                tvPath.text = s
//            }

        // CHON HINH TU GALLERY VA CAMERA
//            scopedMediaPicker.startMediaPicker(
//                mediaType = ScopedMediaPicker.MEDIA_TYPE_IMAGE,
//                actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY or ScopedMediaPicker.ACTION_TYPE_CAMERA
//            ) { pathList, _ ->
//                var s = ""
//                pathList.forEach {
//                    s += it + "\n"
//                }
//                tvPath.text = s
//            }

        // CHON VIDEO TU GALLERY
//            scopedMediaPicker.startMediaPicker(
//                mediaType = ScopedMediaPicker.MEDIA_TYPE_VIDEO,
//                actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY
//            ) { pathList, _ ->
//                var s = ""
//                pathList.forEach {
//                    s += it + "\n"
//                }
//                tvPath.text = s
//            }
    }

    private fun pickFile() {
        scopedMediaPicker.startFilePicker { pathList ->
            var s = ""
            pathList.forEach {
                s += it.fileName + "\n"
            }
            tvPath.text = s
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        scopedMediaPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //TODO onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        scopedMediaPicker.onActivityResult(requestCode, resultCode, data)
    }
}
