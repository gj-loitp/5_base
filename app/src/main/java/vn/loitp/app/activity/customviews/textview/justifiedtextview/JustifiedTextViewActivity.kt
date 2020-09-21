package vn.loitp.app.activity.customviews.textview.justifiedtextview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.textview.justified.LJustifiedTextView
import kotlinx.android.synthetic.main.activity_text_view_justifield.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_justifield)
@LogTag("JustifiedTextViewActivity")
@IsFullScreen(false)
class JustifiedTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jtv = LJustifiedTextView(applicationContext, getString(R.string.large_text))
        ll.addView(jtv)
    }

}
