package vn.loitp.app.activity.customviews.layout.rotatelayout

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_layout_rotate.*
import vn.loitp.app.R

@LogTag("RotateLayoutActivity")
@IsFullScreen(false)
class RotateLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_rotate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btRandomRotate.setSafeOnClickListener {
            val angle = LDeviceUtil.getRandomNumber(360)
            rotateLayout.setAngle(angle)
        }
    }

}
