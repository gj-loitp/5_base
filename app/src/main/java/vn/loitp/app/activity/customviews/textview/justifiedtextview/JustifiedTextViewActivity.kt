package vn.loitp.app.activity.customviews.textview.justifiedtextview

import android.os.Bundle
import android.widget.LinearLayout

import com.core.base.BaseFontActivity
import com.views.textview.justified.LJustifiedTextView

import vn.loitp.app.R

class JustifiedTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ll = findViewById<LinearLayout>(R.id.ll)
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
        return R.layout.activity_justifield_textview
    }
}
