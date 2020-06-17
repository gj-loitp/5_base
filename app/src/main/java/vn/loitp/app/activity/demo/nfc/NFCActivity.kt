package vn.loitp.app.activity.demo.nfc

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class NFCActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_nfc
    }
}
