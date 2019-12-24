package vn.loitp.app.activity.api.coroutine

import com.core.base.BaseFontActivity
import loitp.basemaster.R

class CoroutineAPIActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coroutine_api
    }
}
