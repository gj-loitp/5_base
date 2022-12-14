package vn.loitp.app.activity.function.idleTime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_idle_time.*
import vn.loitp.app.R

@LogTag("IdleTimeActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class IdleTimeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_idle_time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        startIdleTimeHandler(3 * 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = IdleTimeActivity::class.java.simpleName
        }
        updateText(delayMlsIdleTime = null, isIdleTime = null)
    }

    override fun onActivityUserIdleAfterTime(delayMlsIdleTime: Long, isIdleTime: Boolean) {
        super.onActivityUserIdleAfterTime(delayMlsIdleTime, isIdleTime)
        updateText(delayMlsIdleTime = delayMlsIdleTime, isIdleTime = isIdleTime)
    }

    @SuppressLint("SetTextI18n")
    private fun updateText(delayMlsIdleTime: Long?, isIdleTime: Boolean?) {
        logE("onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime")
        val tv = AppCompatTextView(this)
        tv.text =
            "onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime"
        ll.addView(tv)
    }
}
