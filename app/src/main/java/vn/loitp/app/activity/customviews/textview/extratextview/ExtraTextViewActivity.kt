package vn.loitp.app.activity.customviews.textview.extratextview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.textview.extra.LToggleLExtraTextView
import kotlinx.android.synthetic.main.activity_text_view_extra.*
import vn.loitp.app.R

//https://github.com/chuross/extra-textview

@LayoutId(R.layout.activity_text_view_extra)
@LogTag("ExtraTextViewActivity")
@IsFullScreen(false)
class ExtraTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toggleTxt.setOnClickListener { toggleTxt.toggle() }

        btWaiting.setOnClickListener {
            if (toggleTxt.getState() == LToggleLExtraTextView.State.WAITING) {
                toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleTxt.setState(LToggleLExtraTextView.State.WAITING)
            }
        }

        btActive.setOnClickListener {
            if (toggleTxt.getState() == LToggleLExtraTextView.State.ACTIVE) {
                toggleTxt.setState(LToggleLExtraTextView.State.IDLE)
            } else {
                toggleTxt.setState(LToggleLExtraTextView.State.ACTIVE)
            }
        }
    }

}
