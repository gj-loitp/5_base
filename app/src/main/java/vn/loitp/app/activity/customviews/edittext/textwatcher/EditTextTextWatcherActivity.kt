package vn.loitp.app.activity.customviews.edittext.textwatcher

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
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

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
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
