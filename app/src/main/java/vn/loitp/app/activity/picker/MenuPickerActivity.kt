package vn.loitp.app.activity.picker

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.picker.bsimagepicker.BSImagePickerActivity
import vn.loitp.app.activity.picker.crop.CropActivity
import vn.loitp.app.activity.picker.imagepickerwthcop.ImageWithCropActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class MenuPickerActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        findViewById<View>(R.id.bt_bs_image_picker).setOnClickListener(this)
        findViewById<View>(R.id.bt_image_picker_with_crop).setOnClickListener(this)
        findViewById<View>(R.id.bt_crop).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_picker_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_bs_image_picker -> intent = Intent(activity, BSImagePickerActivity::class.java)
            R.id.bt_image_picker_with_crop -> intent = Intent(activity, ImageWithCropActivity::class.java)
            R.id.bt_crop -> intent = Intent(activity, CropActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
