package vn.loitp.app.activity.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_picker_menu.*
import kotlinx.android.synthetic.main.activity_service_menu.*
import loitp.basemaster.R
import vn.loitp.app.activity.picker.bsimagepicker.BSImagePickerActivity
import vn.loitp.app.activity.picker.crop.CropActivity
import vn.loitp.app.activity.picker.imagepickerwthcop.ImageWithCropActivity
import vn.loitp.app.activity.picker.tedimagepicker.DemoTedImagePickerActivity
import vn.loitp.app.activity.service.endlessservice.MainActivity

class MenuServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btEndlessService.setOnClickListener(this)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.btEndlessService -> intent = Intent(activity, MainActivity::class.java)

        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
