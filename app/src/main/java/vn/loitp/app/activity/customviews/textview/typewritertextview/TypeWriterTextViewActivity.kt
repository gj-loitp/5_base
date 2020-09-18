package vn.loitp.app.activity.customviews.textview.typewritertextview

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_type_writer.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_type_writer)
@LogTag("TypeWriterTextViewActivity")
class TypeWriterTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn.setOnClickListener { _ ->
            textView.text = ""
            textView.setCharacterDelay(150)
            textView.animateText("Type Writer Effect")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
