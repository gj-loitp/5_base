package vn.loitp.app.activity.customviews.textview.countdown

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.textview.countdown.LCountDownView
import kotlinx.android.synthetic.main.activity_text_view_count_down.*
import vn.loitp.app.R

@LogTag("CountDownActivity")
@IsFullScreen(false)
class CountDownActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_count_down
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        countDownView.setShowOrHide(false)
        countDownView.setCallback(object : LCountDownView.Callback {
            override fun onTick() {
                // do sth here
            }

            override fun onEnd() {
                btStart.isEnabled = true
                countDownView.setShowOrHide(false)
            }
        })

        btStart.setOnClickListener {
            btStart.isEnabled = false
            countDownView.setShowOrHide(true)
            countDownView.start(5)
        }
    }
}
