package vn.loitp.app.activity.donation

import android.os.Bundle

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.loitp.donate.FrmDonate
import vn.loitp.core.utilities.LScreenUtil

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
