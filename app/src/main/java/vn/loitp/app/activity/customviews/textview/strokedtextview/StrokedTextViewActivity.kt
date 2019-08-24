package vn.loitp.app.activity.customviews.textview.strokedtextview

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

//https://github.com/melihaksoy/StrokedTextView
class StrokedTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_stroked_text_view
    }
}
