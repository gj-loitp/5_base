package vn.loitp.up.a.cv.progress.circularPb

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AProgressCircularProgressBarBinding

@LogTag("CircularProgressBarActivity")
@IsFullScreen(false)
class CircularProgressBarActivity : BaseActivityFont() {

    private lateinit var binding: AProgressCircularProgressBarBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AProgressCircularProgressBarBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CircularProgressBarActivity::class.java.simpleName
        }

        binding.progressBar.progress = 30f
        binding.progressBar.configure()
            .animateProgress(true)
            .maximum(40f)
            .progress(30f)
            .apply()
    }
}
