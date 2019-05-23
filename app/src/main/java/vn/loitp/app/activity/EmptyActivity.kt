package vn.loitp.app.activity

import android.os.Bundle
import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity

class EmptyActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_splash_v3
    }
}
