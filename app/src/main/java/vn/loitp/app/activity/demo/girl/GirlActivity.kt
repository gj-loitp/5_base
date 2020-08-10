package vn.loitp.app.activity.demo.girl

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class GirlActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_girl
    }

}
