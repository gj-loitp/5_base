package vn.loitp.app.activity.picker

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_picker.*
import vn.loitp.app.R
import vn.loitp.app.activity.picker.attachmentManager.AttachmentManagerActivity
import vn.loitp.app.activity.picker.imagePicker.ImagePickerActivity
import vn.loitp.app.activity.picker.numberPicker.NumberPickerActivity
import vn.loitp.app.activity.picker.shiftColorPicker.ShiftColorPickerActivity
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
            this.tvTitle?.text = MenuPickerActivity::class.java.simpleName
        }
        btAttachmentManager.setOnClickListener(this)
        btTimePicker.setOnClickListener(this)
        btNumberPicker.setOnClickListener(this)
        btUnicornFilePickerActivity.setOnClickListener(this)
        btSSImagePicker.setOnClickListener(this)
        btShiftColorPickerActivity.setOnClickListener(this)
        btImagePicker.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btAttachmentManager -> launchActivity(AttachmentManagerActivity::class.java)
            btTimePicker -> launchActivity(TimePickerActivity::class.java)
            btNumberPicker -> launchActivity(NumberPickerActivity::class.java)
            btUnicornFilePickerActivity -> launchActivity(UnicornFilePickerActivity::class.java)
            btSSImagePicker -> launchActivity(MainActivitySSImagePicker::class.java)
            btShiftColorPickerActivity -> launchActivity(ShiftColorPickerActivity::class.java)
            btImagePicker -> launchActivity(ImagePickerActivity::class.java)
        }
    }
}
