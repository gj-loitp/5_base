package vn.loitp.a.picker

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_picker_menu.*
import vn.loitp.R
import vn.loitp.a.picker.attachmentManager.AttachmentManagerActivityFont
import vn.loitp.a.picker.gradientColorPickerBar.GradientColorPickerBarActivityFont
import vn.loitp.a.picker.image.ImagePickerActivityFont
import vn.loitp.a.picker.number.NumberPickerActivityFont
import vn.loitp.a.picker.shiftColor.ShiftColorPickerActivityFont
import vn.loitp.a.picker.ssImage.MainActivitySSImagePickerFont
import vn.loitp.a.picker.time.TimePickerActivityFont
import vn.loitp.a.picker.unicornFile.UnicornFilePickerActivityFont

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPickerActivityFont : BaseActivityFont() {

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
            this.tvTitle?.text = MenuPickerActivityFont::class.java.simpleName
        }
        btAttachmentManager.setSafeOnClickListener {
            launchActivity(AttachmentManagerActivityFont::class.java)
        }
        btGradientColorPickerBar.setSafeOnClickListener {
            launchActivity(GradientColorPickerBarActivityFont::class.java)
        }
        btTimePicker.setSafeOnClickListener {
            launchActivity(TimePickerActivityFont::class.java)
        }
        btNumberPicker.setSafeOnClickListener {
            launchActivity(NumberPickerActivityFont::class.java)
        }
        btUnicornFilePickerActivity.setSafeOnClickListener {
            launchActivity(UnicornFilePickerActivityFont::class.java)
        }
        btSSImagePicker.setSafeOnClickListener {
            launchActivity(MainActivitySSImagePickerFont::class.java)
        }
        btShiftColorPickerActivity.setSafeOnClickListener {
            launchActivity(ShiftColorPickerActivityFont::class.java)
        }
        btImagePicker.setSafeOnClickListener {
            launchActivity(ImagePickerActivityFont::class.java)
        }
    }
}
