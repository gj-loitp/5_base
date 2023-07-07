package vn.loitp.up.a.func.idleTime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AIdleTimeBinding

@LogTag("IdleTimeActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class IdleTimeActivity : BaseActivityFont() {

    private lateinit var binding: AIdleTimeBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIdleTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        startIdleTimeHandler(3 * 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = IdleTimeActivity::class.java.simpleName
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
        binding.ll.addView(tv)
    }
}
