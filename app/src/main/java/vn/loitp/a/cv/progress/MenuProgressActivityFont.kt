package vn.loitp.a.cv.progress

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_pb_menu.*
import vn.loitp.R
import vn.loitp.a.cv.progress.circularPb.CircularProgressBarActivityFont
import vn.loitp.a.cv.progress.circularProgressIndicator.CircularProgressIndicatorActivityFont
import vn.loitp.a.cv.progress.comparingPerformanceBar.ComparingPerformanceBarActivityFont
import vn.loitp.a.cv.progress.segmentedPb.StandardExampleActivityFont
import vn.loitp.a.cv.progress.tileProgressView.TileProgressViewActivityFont
import vn.loitp.a.cv.progress.window.WindowProgressActivityFont

@LogTag("MenuProgressActivity")
@IsFullScreen(false)
class MenuProgressActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_pb_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuProgressActivityFont::class.java.simpleName
        }
        btCircularProgressBar.setSafeOnClickListener {
            launchActivity(CircularProgressBarActivityFont::class.java)
        }
        btCircularProgressIndicator.setSafeOnClickListener {
            launchActivity(CircularProgressIndicatorActivityFont::class.java)
        }
        btWindow.setSafeOnClickListener {
            launchActivity(WindowProgressActivityFont::class.java)
        }
        btSegmentedProgressBar.setSafeOnClickListener {
            launchActivity(StandardExampleActivityFont::class.java)
        }
        btComparingPerformanceBar.setSafeOnClickListener {
            launchActivity(ComparingPerformanceBarActivityFont::class.java)
        }
        btTileProgressView.setSafeOnClickListener {
            launchActivity(TileProgressViewActivityFont::class.java)
        }
    }
}
