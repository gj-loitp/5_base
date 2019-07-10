package vn.loitp.app.activity.more

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.loitp.more.FrmMore
import com.core.utilities.LScreenUtil
import loitp.basemaster.R

class MoreActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LScreenUtil.addFragment(activity, R.id.fl_container, FrmMore(), false)
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
