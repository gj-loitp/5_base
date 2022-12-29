package vn.loitp.a.picker

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_picker_menu.*
import vn.loitp.R
import vn.loitp.a.picker.attachmentManager.AttachmentManagerActivity
import vn.loitp.a.picker.gradientColorPickerBar.GradientColorPickerBarActivity
import vn.loitp.a.picker.image.ImagePickerActivity
import vn.loitp.a.picker.number.NumberPickerActivity
import vn.loitp.a.picker.shiftColor.ShiftColorPickerActivity
import vn.loitp.a.picker.ssImage.MainActivitySSImagePicker
import vn.loitp.a.picker.time.TimePickerActivity
import vn.loitp.a.picker.unicornFile.UnicornFilePickerActivity

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPickerActivity : BaseFontActivity() {

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
        btAttachmentManager.setSafeOnClickListener {
            launchActivity(AttachmentManagerActivity::class.java)
        }
        btGradientColorPickerBar.setSafeOnClickListener {
            launchActivity(GradientColorPickerBarActivity::class.java)
        }
        btTimePicker.setSafeOnClickListener {
            launchActivity(TimePickerActivity::class.java)
        }
        btNumberPicker.setSafeOnClickListener {
            launchActivity(NumberPickerActivity::class.java)
        }
        btUnicornFilePickerActivity.setSafeOnClickListener {
            launchActivity(UnicornFilePickerActivity::class.java)
        }
        btSSImagePicker.setSafeOnClickListener {
            launchActivity(MainActivitySSImagePicker::class.java)
        }
        btShiftColorPickerActivity.setSafeOnClickListener {
            launchActivity(ShiftColorPickerActivity::class.java)
        }
        btImagePicker.setSafeOnClickListener {
            launchActivity(ImagePickerActivity::class.java)
        }
    }
}
