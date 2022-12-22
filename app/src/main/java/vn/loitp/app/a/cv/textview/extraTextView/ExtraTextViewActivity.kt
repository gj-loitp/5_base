package vn.loitp.app.a.cv.textview.extraTextView

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.views.tv.extra.LToggleLExtraTextView
import kotlinx.android.synthetic.main.activity_text_view_extra.*
import vn.loitp.R

//https://github.com/chuross/extra-textview
@LogTag("ExtraTextViewActivity")
@IsFullScreen(false)
class ExtraTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_extra
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        toggleTxt.setSafeOnClickListener {
            toggleTxt.toggle()
        }

        btWaiting.setSafeOnClickListener {
            if (toggleTxt.getState() == LToggleLExtraTextView.State.WAITING) {
                toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleTxt.setState(LToggleLExtraTextView.State.WAITING)
            }
        }

        btActive.setSafeOnClickListener {
            if (toggleTxt.getState() == LToggleLExtraTextView.State.ACTIVE) {
                toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleTxt.setState(LToggleLExtraTextView.State.ACTIVE)
            }
        }
    }

}
