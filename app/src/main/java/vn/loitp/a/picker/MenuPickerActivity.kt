package vn.loitp.a.picker

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_picker_menu.*
import vn.loitp.R
import vn.loitp.a.picker.attachmentManager.AttachmentManagerActivity
import vn.loitp.a.picker.image.ImagePickerActivity
import vn.loitp.a.picker.number.NumberPickerActivity
import vn.loitp.app.a.picker.shiftColor.ShiftColorPickerActivity
import vn.loitp.app.a.picker.ssImage.MainActivitySSImagePicker
import vn.loitp.app.a.picker.time.TimePickerActivity
import vn.loitp.app.a.picker.unicornFile.UnicornFilePickerActivity

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPickerActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_picker_menu
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
