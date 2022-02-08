package vn.loitp.app.activity.picker.scopedmediapicker

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.media.scopemediapicker.ScopedMediaPicker
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_scoped_media_picker.*
import vn.loitp.app.R

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
                    onBackPressed()
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
            this.tvTitle?.text = javaClass.simpleName
        }
        btnCapture.setOnClickListener {
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

//            scopedMediaPicker.startMediaPicker(
//                mediaType = ScopedMediaPicker.MEDIA_TYPE_IMAGE,
//                actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY
//            ) { pathList, type ->
//                Log.e("FilePath", pathList.toString())
//            }

//            scopedMediaPicker.startMediaPicker(
//                mediaType = ScopedMediaPicker.MEDIA_TYPE_VIDEO,
//                actionType = ScopedMediaPicker.ACTION_TYPE_GALLERY
//            ) { pathList, type ->
//                Log.e("FilePath", pathList.toString())
//            }
        }
        btnFile.setOnClickListener {
            scopedMediaPicker.startFilePicker() { pathList ->
                var s = ""
                pathList.forEach {
                    s += it.fileName + "\n"
                }
                tvPath.text = s
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        scopedMediaPicker.onActivityResult(requestCode, resultCode, data)
    }
}