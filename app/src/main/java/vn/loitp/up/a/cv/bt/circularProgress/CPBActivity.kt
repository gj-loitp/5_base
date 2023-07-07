package vn.loitp.up.a.cv.bt.circularProgress

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ACpbBinding

@LogTag("CPBActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CPBActivity : BaseActivityFont() {
    private lateinit var binding: ACpbBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACpbBinding.inflate(layoutInflater)
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/dmytrodanylyk/circular-progress-button"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CPBActivity::class.java.simpleName
        }

        binding.bt1.setSafeOnClickListener {
            launchActivity(Sample1Activity::class.java)
        }
        binding.bt2.setSafeOnClickListener {
            launchActivity(Sample2Activity::class.java)
        }
        binding.bt3.setSafeOnClickListener {
            launchActivity(Sample3Activity::class.java)
        }
        binding.bt4.setSafeOnClickListener {
            launchActivity(Sample4Activity::class.java)
        }
        binding.bt5.setSafeOnClickListener {
            launchActivity(Sample5Activity::class.java)
        }
    }
}
