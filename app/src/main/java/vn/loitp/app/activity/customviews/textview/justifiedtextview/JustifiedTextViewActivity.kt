package vn.loitp.app.activity.customviews.textview.justifiedtextview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.textview.justified.LJustifiedTextView
import kotlinx.android.synthetic.main.activity_text_view_justifield.*
import vn.loitp.app.R

class JustifiedTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jtv = LJustifiedTextView(applicationContext, getString(R.string.large_text))
        ll.addView(jtv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_justifield
    }
}
