package vn.loitp.a.demo.rss

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.addFragment
import vn.loitp.R

@LogTag("RSSActivity")
@IsFullScreen(false)
class RSSActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_rss
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        this.addFragment(
            containerFrameLayoutIdRes = R.id.rlContainer,
            fragment = RssFragment.newInstance("https://vnexpress.net/rss/tin-moi-nhat.rss/"),
            isAddToBackStack = false
        )
    }
}
