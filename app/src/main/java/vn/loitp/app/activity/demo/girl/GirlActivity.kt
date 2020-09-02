package vn.loitp.app.activity.demo.girl

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.helper.girl.FrmGirl
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

class GirlActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_girl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.flContainer, fragment = FrmGirl(), isAddToBackStack = false)
    }

}
