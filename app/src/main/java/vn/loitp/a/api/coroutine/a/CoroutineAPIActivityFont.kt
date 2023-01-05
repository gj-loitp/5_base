package vn.loitp.a.api.coroutine.a

import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import vn.loitp.R

@LogTag("CoroutineAPIActivity")
@IsFullScreen(false)
class CoroutineAPIActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_coroutine_api
    }
}
