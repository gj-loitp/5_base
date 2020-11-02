package vn.loitp.app.activity

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("EmptyActivity")
@IsFullScreen(false)
class EmptyActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_splash_v3
    }
}
