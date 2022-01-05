package vn.loitp.app.activity.customviews.edittext.textwatcher

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_edittext_text_watcher.*
import vn.loitp.app.R

@LogTag("EditTextTextWatcherActivity")
@IsFullScreen(false)
class EditTextTextWatcherActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_edittext_text_watcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var text = ""

        LUIUtil.addTextChangedListener(
            editText = editText,
            delayInMls = 1000, afterTextChanged = { s ->
                text += s + "\n"
                textView.text = text
            }
        )
    }
}
