package vn.loitp.up.a.demo.rss

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import vn.loitp.R
import vn.loitp.databinding.ARssBinding

@LogTag("RSSActivity")
@IsFullScreen(false)
class RSSActivity : BaseActivityFont() {
    private lateinit var binding: ARssBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARssBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
