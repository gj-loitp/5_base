package vn.loitp.app.activity.customviews.textview.extratextview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.textview.extra.LToggleLExtraTextView
import kotlinx.android.synthetic.main.activity_extra_textview.*
import vn.loitp.app.R

//https://github.com/chuross/extra-textview
class ExtraTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toggleText = findViewById<LToggleLExtraTextView>(R.id.toggle_txt)
        toggleText.setOnClickListener { toggleText.toggle() }

        waitingBtn.setOnClickListener {
            if (toggleText.getState() == LToggleLExtraTextView.State.WAITING) {
                toggleText.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleText.setState(LToggleLExtraTextView.State.WAITING)
            }
        }

        activeBtn.setOnClickListener {
            if (toggleText.getState() == LToggleLExtraTextView.State.ACTIVE) {
                toggleText.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleText.setState(LToggleLExtraTextView.State.ACTIVE)
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
