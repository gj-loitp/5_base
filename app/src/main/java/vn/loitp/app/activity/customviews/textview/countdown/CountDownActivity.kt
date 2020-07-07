package vn.loitp.app.activity.customviews.textview.countdown

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.textview.countdown.LCountDownView
import kotlinx.android.synthetic.main.activity_text_view_count_down.*
import vn.loitp.app.R

class CountDownActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countDownView.setShowOrHide(false)
        countDownView.setCallback(object : LCountDownView.Callback {
            override fun onTick() {
                //do sth here
            }

            override fun onEnd() {
                btStart.isEnabled = true
                countDownView.setShowOrHide(false)
            }
        })

        btStart.setOnClickListener { _ ->
            btStart.isEnabled = false
            countDownView.setShowOrHide(true)
            countDownView.start(5)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_count_down
    }
}
