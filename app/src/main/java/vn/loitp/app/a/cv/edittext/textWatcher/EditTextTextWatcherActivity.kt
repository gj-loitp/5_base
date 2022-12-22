package vn.loitp.app.a.cv.edittext.textWatcher

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_text_watcher_edittext.*
import vn.loitp.R

@LogTag("EditTextTextWatcherActivity")
@IsFullScreen(false)
class EditTextTextWatcherActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_watcher_edittext
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
            this.tvTitle?.text = EditTextTextWatcherActivity::class.java.simpleName
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
