package vn.loitp.app.activity.picker.tedimagepicker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import gun0912.tedimagepicker.builder.TedRxImagePicker
import kotlinx.android.synthetic.main.activity_ted_image_picker.*
import vn.loitp.app.R

@LogTag("TedImagePickerActivity")
@IsFullScreen(false)
class TedImagePickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ted_image_picker
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
            this.tvTitle?.text = TedImagePickerActivity::class.java.simpleName
        }

        btnRxSingle.setSafeOnClickListener {
            onSingle()
        }
        btnRxMulti.setSafeOnClickListener {
        }
    }

    private fun onSingle() {
        TedRxImagePicker.with(this)
            .start()
            .subscribe({ uri ->
            }, Throwable::printStackTrace)
    }
}
