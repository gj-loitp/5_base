package vn.loitp.app.activity.customviews.textview.justifiedtextview

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("JustifiedTextViewActivity")
@IsFullScreen(false)
class JustifiedTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_justified_text_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
    }
}
