package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.activity_switch_appcompat.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_switch_appcompat)
@LogTag("AppcompatSwitchActivity")
@IsFullScreen(false)
class AppcompatSwitchActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lswitch)

        val isDarkTheme = LSharedPrefsUtil.instance.getBoolean(Constants.KEY_IS_DARK_THEME, true)
        sw.isChecked = isDarkTheme

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                LSharedPrefsUtil.instance.putBoolean(Constants.KEY_IS_DARK_THEME, true)
            } else {
                LSharedPrefsUtil.instance.putBoolean(Constants.KEY_IS_DARK_THEME, false)
            }
        }
    }

}
