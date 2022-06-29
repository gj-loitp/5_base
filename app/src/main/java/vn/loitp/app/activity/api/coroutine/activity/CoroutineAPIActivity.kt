package vn.loitp.app.activity.api.coroutine.activity

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("CoroutineAPIActivity")
@IsFullScreen(false)
class CoroutineAPIActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_coroutine
    }
}
