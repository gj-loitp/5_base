package vn.loitp.app.activity.customviews.edittext.textwatcher

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_edittext_text_watcher.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_edittext_text_watcher)
@LogTag("EditTextTextWatcherActivity")
class EditTextTextWatcherActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var text = ""

        LUIUtil.addTextChangedListener(editText, 1000, afterTextChanged = { s ->
            text += s + "\n"
            textView.text = text
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }
}
