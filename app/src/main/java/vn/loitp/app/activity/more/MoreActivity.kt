package vn.loitp.app.activity.more

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.helper.more.FrmMore
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

class MoreActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LScreenUtil.addFragment(activity, R.id.flContainer, FrmMore(), false)
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
