package vn.loitp.app.activity.demo.girl

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.girl.FrmGirl
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LayoutId(R.layout.activity_girl)
@LogTag("GirlActivity")
@IsFullScreen(false)
class GirlActivity : BaseFontActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(activity = this, containerFrameLayoutIdRes = R.id.flContainer, fragment = FrmGirl(), isAddToBackStack = false)
    }

}
