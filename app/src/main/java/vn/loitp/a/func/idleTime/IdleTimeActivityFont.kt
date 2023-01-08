package vn.loitp.a.func.idleTime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_idle_time.*
import vn.loitp.R

@LogTag("IdleTimeActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class IdleTimeActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_idle_time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        startIdleTimeHandler(3 * 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = IdleTimeActivityFont::class.java.simpleName
        }
        updateText(delayMlsIdleTime = null, isIdleTime = null)
    }

    override fun onActivityUserIdleAfterTime(
        delayMlsIdleTime: Long,
        isIdleTime: Boolean
    ) {
        super.onActivityUserIdleAfterTime(delayMlsIdleTime, isIdleTime)
        updateText(delayMlsIdleTime = delayMlsIdleTime, isIdleTime = isIdleTime)
    }

    @SuppressLint("SetTextI18n")
    private fun updateText(
        delayMlsIdleTime: Long?,
        isIdleTime: Boolean?
    ) {
        logE("onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime")
        val tv = AppCompatTextView(this)
        tv.text =
            "onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime $isIdleTime"
        ll.addView(tv)
    }
}
