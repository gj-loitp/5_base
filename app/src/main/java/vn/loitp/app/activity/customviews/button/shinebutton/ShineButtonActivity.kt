package vn.loitp.app.activity.customviews.button.shinebutton

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

class ShineButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_shine
    }
}
