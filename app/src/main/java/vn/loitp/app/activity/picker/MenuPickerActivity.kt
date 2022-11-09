package vn.loitp.app.activity.picker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_picker.*
import vn.loitp.app.R
import vn.loitp.app.activity.picker.attachmentManager.AttachmentManagerActivity
import vn.loitp.app.activity.picker.numberPicker.NumberPickerActivity
import vn.loitp.app.activity.picker.ssImagePicker.ui.MainActivitySSImagePicker
import vn.loitp.app.activity.picker.timePicker.TimePickerActivity
import vn.loitp.app.activity.picker.unicornFilePicker.UnicornFilePickerActivity

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPickerActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_picker
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuPickerActivity::class.java.simpleName
        }
        btAttachmentManager.setOnClickListener(this)
        btTimePicker.setOnClickListener(this)
        btNumberPicker.setOnClickListener(this)
        btUnicornFilePickerActivity.setOnClickListener(this)
        btSSImagePicker.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btAttachmentManager -> Intent(this, AttachmentManagerActivity::class.java)
            btTimePicker -> Intent(this, TimePickerActivity::class.java)
            btNumberPicker -> Intent(this, NumberPickerActivity::class.java)
            btUnicornFilePickerActivity -> Intent(this, UnicornFilePickerActivity::class.java)
            btSSImagePicker -> Intent(this, MainActivitySSImagePicker::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
