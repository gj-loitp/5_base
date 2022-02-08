package vn.loitp.app.activity.picker

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_picker_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.picker.basic.BasicPickerActivity
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

        btTimePicker.setOnClickListener(this)
        btScopedMediaPicker.setOnClickListener(this)
        btBasicPicker.setOnClickListener(this)
        btNumbePicker.setOnClickListener(this)
        btUnicornFilePickerActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btTimePicker -> Intent(this, TimePickerActivity::class.java)
            btScopedMediaPicker -> Intent(this, ScopedMediaPickerActivity::class.java)
            btBasicPicker -> Intent(this, BasicPickerActivity::class.java)
            btNumbePicker -> Intent(this, NumberPickerActivity::class.java)
            btUnicornFilePickerActivity -> Intent(this, UnicornFilePickerActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
