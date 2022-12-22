package vn.loitp.a.api.coroutine.activity

import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import vn.loitp.R

@LogTag("CoroutineAPIActivity")
@IsFullScreen(false)
class CoroutineAPIActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coroutine_api
    }
}
