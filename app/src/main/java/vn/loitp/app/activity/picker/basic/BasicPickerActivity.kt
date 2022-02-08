package vn.loitp.app.activity.picker.basic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_basic_picker.*
import vn.loitp.app.R

@LogTag("TedImagePickerActivity")
@IsFullScreen(false)
class BasicPickerActivity : BaseFontActivity() {

    companion object {
        const val PICK_IMAGE_REQUEST = 123
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_basic_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
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
                            url = "https://github.com/ParkSangGwon/TedImagePicker"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = BasicPickerActivity::class.java.simpleName
        }
        btSingleImage.setSafeOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select images"),
                PICK_IMAGE_REQUEST
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data == null) {
                return
            }
            if (resultCode == RESULT_OK) {
                data.clipData?.let { cd ->
                    val count = cd.itemCount
                    var s = ""
                    for (i in 0 until count) {
                        val imageUri = cd.getItemAt(i).uri
                        logD(">>>imageUri ${imageUri.path}")
                        s += imageUri.path + "\n"
                    }
                    tvPath.text = s
                }
            } else if (data.data != null) {
                val imagePath = data.data?.path
                logD(">>>imagePath $imagePath")
            }
        }
    }
}
