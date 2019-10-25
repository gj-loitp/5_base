package vn.loitp.app.activity.customviews.textview.circletextview

import android.os.Bundle
import android.text.SpannableStringBuilder

import com.core.base.BaseFontActivity
import com.views.textview.circle.LCircleTextView

import loitp.basemaster.R

class CircleTextViewActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = findViewById<LCircleTextView>(R.id.circle_text_view)
        textView.setTextSize(R.dimen.txt_18)

        val builder = SpannableStringBuilder(resources.getString(R.string.hello_world))
        textView.setText(builder)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circle_textview
    }

}
