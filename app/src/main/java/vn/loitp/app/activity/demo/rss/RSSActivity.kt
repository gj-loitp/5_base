package vn.loitp.app.activity.demo.rss

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LogTag("RSSActivity")
@IsFullScreen(false)
class RSSActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rss
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.rlContainer,
                fragment = RssFragment.newInstance("https://vnexpress.net/rss/tin-moi-nhat.rss/"),
                isAddToBackStack = false
        )

    }
}
