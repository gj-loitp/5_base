package vn.loitp.app.activity.picker

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_picker_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.picker.attachmentmanager.AttachmentManagerActivity
import vn.loitp.app.activity.picker.numberpicker.NumberPickerActivity
import vn.loitp.app.activity.picker.scopedmediapicker.ScopedMediaPickerActivity
import vn.loitp.app.activity.picker.timepicker.TimePickerActivity
import vn.loitp.app.activity.picker.unicornfilepicker.UnicornFilePickerActivity

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
class MenuPickerActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_picker_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAttachmentManager.setOnClickListener(this)
        btTimePicker.setOnClickListener(this)
        btScopedMediaPicker.setOnClickListener(this)
        btNumberPicker.setOnClickListener(this)
        btUnicornFilePickerActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btAttachmentManager -> Intent(this, AttachmentManagerActivity::class.java)
            btTimePicker -> Intent(this, TimePickerActivity::class.java)
            btScopedMediaPicker -> Intent(this, ScopedMediaPickerActivity::class.java)
            btNumberPicker -> Intent(this, NumberPickerActivity::class.java)
            btUnicornFilePickerActivity -> Intent(this, UnicornFilePickerActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
