package vn.loitp.up.a.cv.tv.countDown

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.tv.countDown.LCountDownView
import vn.loitp.R
import vn.loitp.databinding.ATvCountDownBinding

@LogTag("CountDownActivity")
@IsFullScreen(false)
class CountDownActivity : BaseActivityFont() {
    private lateinit var binding: ATvCountDownBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvCountDownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CountDownActivity::class.java.simpleName
        }
        binding.countDownView.setShowOrHide(false)
        binding.countDownView.setCallback(object : LCountDownView.Callback {
            override fun onTick() {
                // do sth here
            }

            override fun onEnd() {
                binding.btStart.isEnabled = true
                binding.countDownView.setShowOrHide(false)
            }
        })

        binding.btStart.setSafeOnClickListener {
            binding.btStart.isEnabled = false
            binding.countDownView.setShowOrHide(true)
            binding.countDownView.start(5)
        }
    }
}
