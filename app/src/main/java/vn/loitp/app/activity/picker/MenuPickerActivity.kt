package vn.loitp.app.activity.picker

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_picker_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.picker.bsimagepicker.BSImagePickerActivity
import vn.loitp.app.activity.picker.crop.CropActivity
import vn.loitp.app.activity.picker.imagepickerwthcop.ImageWithCropActivity
import vn.loitp.app.activity.picker.numberpicker.NumberPickerActivity
import vn.loitp.app.activity.picker.tedimagepicker.DemoTedImagePickerActivity
import vn.loitp.app.activity.picker.timepicker.TimePickerActivity

@LayoutId(R.layout.activity_picker_menu)
@LogTag("MenuPickerActivity")
class MenuPickerActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btBsImagePicker.setOnClickListener(this)
        btImagePickerWithCrop.setOnClickListener(this)
        btCrop.setOnClickListener(this)
        btTimePicker.setOnClickListener(this)
        btNumbePicker.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBsImagePicker -> intent = Intent(activity, BSImagePickerActivity::class.java)
            btImagePickerWithCrop -> intent = Intent(activity, ImageWithCropActivity::class.java)
            btCrop -> intent = Intent(activity, CropActivity::class.java)
            btTedImagePicker -> intent = Intent(activity, DemoTedImagePickerActivity::class.java)
            btTimePicker -> intent = Intent(activity, TimePickerActivity::class.java)
            btNumbePicker -> intent = Intent(activity, NumberPickerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
