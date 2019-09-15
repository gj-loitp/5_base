package vn.loitp.app.activity.donation

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.helper.donate.FrmDonate
import com.core.utilities.LScreenUtil
import loitp.basemaster.R

class DonationActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LScreenUtil.addFragment(activity, R.id.fl_container, FrmDonate(), false)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_more
    }
}
