package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.activity_switch_appcompat.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_switch_appcompat)
class AppcompatSwitchActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView.text = LStoreUtil.readTxtFromRawFolder(context = activity, nameOfRawFile = R.raw.lswitch)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
