package vn.loitp.app.activity.api.coroutine.activity

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_api_coroutine)
@LogTag("ShadowViewHelperActivity")
class CoroutineAPIActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }
}
