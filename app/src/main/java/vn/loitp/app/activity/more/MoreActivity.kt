package vn.loitp.app.activity.more

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.more.FrmMore
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LayoutId(R.layout.activity_more)
@LogTag("MoreActivity")
@IsFullScreen(false)
class MoreActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(activity = this, containerFrameLayoutIdRes = R.id.flContainer, fragment = FrmMore(), isAddToBackStack = false)
    }

}
