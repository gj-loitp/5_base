package vn.loitp.app.activity.donation

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.donate.FrmDonate
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LayoutId(R.layout.activity_more)
@LogTag("DonationActivity")
class DonationActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.flContainer, fragment = FrmDonate(), isAddToBackStack = false)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
