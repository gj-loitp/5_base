package vn.loitp.up.a.cv.progress

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.a.cv.progress.circularPb.CircularProgressBarActivityFont
import vn.loitp.a.cv.progress.circularProgressIndicator.CircularProgressIndicatorActivityFont
import vn.loitp.a.cv.progress.comparingPerformanceBar.ComparingPerformanceBarActivityFont
import vn.loitp.a.cv.progress.progressView.ProgressViewActivity
import vn.loitp.databinding.APbMenuBinding
import vn.loitp.up.a.cv.progress.segmentedPb.StandardExampleActivity
import vn.loitp.up.a.cv.progress.tileProgressView.TileProgressViewActivity
import vn.loitp.up.a.cv.progress.window.WindowProgressActivity

@LogTag("MenuProgressActivity")
@IsFullScreen(false)
class MenuProgressActivity : BaseActivityFont() {
    private lateinit var binding: APbMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APbMenuBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuProgressActivity::class.java.simpleName
        }
        binding.btCircularProgressBar.setSafeOnClickListener {
            launchActivity(CircularProgressBarActivityFont::class.java)
        }
        binding.btCircularProgressIndicator.setSafeOnClickListener {
            launchActivity(CircularProgressIndicatorActivityFont::class.java)
        }
        binding.btSegmentedProgressBar.setSafeOnClickListener {
            launchActivity(StandardExampleActivity::class.java)
        }
        binding.btComparingPerformanceBar.setSafeOnClickListener {
            launchActivity(ComparingPerformanceBarActivityFont::class.java)
        }
        binding.btTileProgressView.setSafeOnClickListener {
            launchActivity(TileProgressViewActivity::class.java)
        }
        binding.btWindow.setSafeOnClickListener {
            launchActivity(WindowProgressActivity::class.java)
        }
        binding.btProgressView.setSafeOnClickListener {
            launchActivity(ProgressViewActivity::class.java)
        }
    }
}
