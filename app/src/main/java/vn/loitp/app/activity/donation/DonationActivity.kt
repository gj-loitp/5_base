package vn.loitp.app.activity.donation

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.donate.FrmDonate
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LayoutId(R.layout.activity_more)
@LogTag("DonationActivity")
@IsFullScreen(false)
class DonationActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(activity = this, containerFrameLayoutIdRes = R.id.flContainer, fragment = FrmDonate(), isAddToBackStack = false)
    }

}
