package vn.loitp.app.activity.customviews.textview.circletextview

import android.os.Bundle
import android.text.SpannableStringBuilder
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_circle.*
import vn.loitp.app.R

@LogTag("CircleTextViewActivity")
@IsFullScreen(false)
class CircleTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_circle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvCircle.setTextSize(R.dimen.txt_medium)
        val builder = SpannableStringBuilder(resources.getString(R.string.hello_world))
        tvCircle.setText(builder)
    }

}
