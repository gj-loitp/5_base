package vn.loitp.a.cv.tv.countDown

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.tv.countDown.LCountDownView
import kotlinx.android.synthetic.main.a_tv_count_down.*
import vn.loitp.R

@LogTag("CountDownActivity")
@IsFullScreen(false)
class CountDownActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_tv_count_down
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CountDownActivity::class.java.simpleName
        }
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

        btStart.setSafeOnClickListener {
            btStart.isEnabled = false
            countDownView.setShowOrHide(true)
            countDownView.start(5)
        }
    }
}
