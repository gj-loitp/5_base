package vn.loitp.app.activity.demo.rss

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import vn.loitp.app.R

@LogTag("RSSActivity")
@IsFullScreen(false)
class RSSActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rss
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LScreenUtil.addFragment(
            activity = this,
            containerFrameLayoutIdRes = R.id.rlContainer,
            fragment = RssFragment.newInstance("https://vnexpress.net/rss/tin-moi-nhat.rss/"),
            isAddToBackStack = false
        )
    }
}
