package vn.loitp.up.a.picker

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.picker.attachmentManager.AttachmentManagerActivityFont
import vn.loitp.a.picker.gradientColorPickerBar.GradientColorPickerBarActivityFont
import vn.loitp.a.picker.image.ImagePickerActivityFont
import vn.loitp.a.picker.number.NumberPickerActivityFont
import vn.loitp.a.picker.shiftColor.ShiftColorPickerActivityFont
import vn.loitp.a.picker.ssImage.MainActivitySSImagePickerFont
import vn.loitp.databinding.APickerMenuBinding
import vn.loitp.up.a.picker.time.TimePickerActivity
import vn.loitp.up.a.picker.unicornFile.UnicornFilePickerActivity

@LogTag("MenuPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPickerActivity : BaseActivityFont() {

    private lateinit var binding: APickerMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuPickerActivity::class.java.simpleName
        }
        binding.btAttachmentManager.setSafeOnClickListener {
            launchActivity(AttachmentManagerActivityFont::class.java)
        }
        binding.btGradientColorPickerBar.setSafeOnClickListener {
            launchActivity(GradientColorPickerBarActivityFont::class.java)
        }
        binding.btTimePicker.setSafeOnClickListener {
            launchActivity(TimePickerActivity::class.java)
        }
        binding.btNumberPicker.setSafeOnClickListener {
            launchActivity(NumberPickerActivityFont::class.java)
        }
        binding.btUnicornFilePickerActivity.setSafeOnClickListener {
            launchActivity(UnicornFilePickerActivity::class.java)
        }
        binding.btSSImagePicker.setSafeOnClickListener {
            launchActivity(MainActivitySSImagePickerFont::class.java)
        }
        binding.btShiftColorPickerActivity.setSafeOnClickListener {
            launchActivity(ShiftColorPickerActivityFont::class.java)
        }
        binding.btImagePicker.setSafeOnClickListener {
            launchActivity(ImagePickerActivityFont::class.java)
        }
    }
}
