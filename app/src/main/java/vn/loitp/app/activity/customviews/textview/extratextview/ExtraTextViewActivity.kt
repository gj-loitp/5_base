package vn.loitp.app.activity.customviews.textview.extratextview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.textview.extratextview.ToggleExtraTextView
import kotlinx.android.synthetic.main.activity_extra_textview.*
import loitp.basemaster.R

//https://github.com/chuross/extra-textview
class ExtraTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toggleText = findViewById<ToggleExtraTextView>(R.id.toggle_txt)
        toggleText.setOnClickListener { toggleText.toggle() }

        waitingBtn.setOnClickListener {
            if (toggleText.state == ToggleExtraTextView.State.WAITING) {
                toggleText.state = ToggleExtraTextView.State.IDLE
            } else {
                toggleText.state = ToggleExtraTextView.State.WAITING
            }
        }

        activeBtn.setOnClickListener {
            if (toggleText.state == ToggleExtraTextView.State.ACTIVE) {
                toggleText.state = ToggleExtraTextView.State.IDLE
            } else {
                toggleText.state = ToggleExtraTextView.State.ACTIVE
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_extra_textview
    }
}
