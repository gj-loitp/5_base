package vn.loitp.a.cv.et.textWatcher

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_et_text_watcher.*
import vn.loitp.R

@LogTag("EditTextTextWatcherActivity")
@IsFullScreen(false)
class EditTextTextWatcherActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_et_text_watcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = EditTextTextWatcherActivityFont::class.java.simpleName
        }
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
