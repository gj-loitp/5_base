package vn.loitp.app.activity.customviews.textview.typewritertextview

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_type_writer.*
import vn.loitp.app.R

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

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_type_writer
    }

}
