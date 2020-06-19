package vn.loitp.app.activity.customviews.layout.rotatelayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import kotlinx.android.synthetic.main.activity_layout_rotate.*
import vn.loitp.app.R

class RotateLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btRandomRotate.setOnClickListener {
            val angle = LDeviceUtil.getRandomNumber(360)
            rotateLayout.angle = angle
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_rotate
    }
}
