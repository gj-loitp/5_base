package vn.loitp.app.activity.picker.unicornfilepicker

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_unicorn_file_picker.*
import vn.loitp.app.R

@LogTag("UnicornFilePickerActivity")
@IsFullScreen(false)
class UnicornFilePickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_unicorn_file_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btVideo.setSafeOnClickListener {
        }
    }
}
