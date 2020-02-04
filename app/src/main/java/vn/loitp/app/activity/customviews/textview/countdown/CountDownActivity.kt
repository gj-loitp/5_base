package vn.loitp.app.activity.customviews.textview.countdown

import android.os.Bundle
import android.widget.Button
import com.core.base.BaseFontActivity
import com.views.textview.countdown.LCountDownView
import loitp.basemaster.R

class CountDownActivity : BaseFontActivity() {

    private lateinit var btStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btStart = findViewById(R.id.bt_start)

        val lCountDownView = findViewById<LCountDownView>(R.id.l_countdown)
        lCountDownView.setShowOrHide(false)
        lCountDownView.setCallback(object : LCountDownView.Callback {
            override fun onTick() {
                //do sth here
            }

            override fun onEnd() {
                btStart.isEnabled = true
                lCountDownView.setShowOrHide(false)
            }
        })

        btStart.setOnClickListener { _ ->
            btStart.isEnabled = false
            lCountDownView.setShowOrHide(true)
            lCountDownView.start(5)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_count_down_textview
    }
}
