package vn.loitp.up.a.cv.progress.comparingPerformanceBar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AComparingPerformanceBarBinding

@LogTag("ComparingPerformanceBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class ComparingPerformanceBarActivity : BaseActivityFont() {
    private lateinit var binding: AComparingPerformanceBarBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AComparingPerformanceBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
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
                            url = "https://github.com/cliffgr/ComparingPerformanceBar"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ComparingPerformanceBarActivity::class.java.simpleName
        }

        binding.percentageProgressBar.setProgress(30.0f)
        binding.valuesProgressBar.setValues(100.0f, 20.0f)
        binding.valuesProgressBarPercent.setValues(10.0f, 7.0f)
    }
}
