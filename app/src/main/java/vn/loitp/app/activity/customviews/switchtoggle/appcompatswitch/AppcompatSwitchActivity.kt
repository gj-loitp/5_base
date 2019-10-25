package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.activity_appcompat_switch.*
import loitp.basemaster.R

class AppcompatSwitchActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv.text = LStoreUtil.readTxtFromRawFolder(activity, R.raw.lswitch)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_appcompat_switch
    }
}
