package vn.loitp.app.activity.customviews.textview.extratextview

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.setSafeOnClickListener
import com.loitpcore.views.textview.extra.LToggleLExtraTextView
import kotlinx.android.synthetic.main.activity_text_view_extra.*
import vn.loitp.app.R

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
