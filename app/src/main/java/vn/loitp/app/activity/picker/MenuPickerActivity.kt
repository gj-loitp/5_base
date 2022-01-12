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
import vn.loitp.app.activity.picker.crop.CropActivity
import vn.loitp.app.activity.picker.imagepickerwthcop.ImageWithCropActivity
import vn.loitp.app.activity.picker.numberpicker.NumberPickerActivity
import vn.loitp.app.activity.picker.tedimagepicker.DemoTedImagePickerActivity
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

        btImagePickerWithCrop.setOnClickListener(this)
        btCrop.setOnClickListener(this)
        btTimePicker.setOnClickListener(this)
        btNumbePicker.setOnClickListener(this)
        btTedImagePicker.setOnClickListener(this)
        btUnicornFilePickerActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btImagePickerWithCrop -> intent = Intent(this, ImageWithCropActivity::class.java)
            btCrop -> intent = Intent(this, CropActivity::class.java)
            btTedImagePicker -> intent = Intent(this, DemoTedImagePickerActivity::class.java)
            btTimePicker -> intent = Intent(this, TimePickerActivity::class.java)
            btNumbePicker -> intent = Intent(this, NumberPickerActivity::class.java)
            btUnicornFilePickerActivity -> intent = Intent(this, UnicornFilePickerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
