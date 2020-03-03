package vn.loitp.app.activity.customviews.edittext.textwatcher

import android.os.Bundle
import com.core.base.BaseActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_edittext_text_watcher.*
import vn.loitp.app.R

class EditTextTextWatcherActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var text = ""

        LUIUtil.addTextChangedListener(et, 1000, afterTextChanged = { s ->
            text += s + "\n"
            tv.text = text
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_edittext_text_watcher
    }

}
