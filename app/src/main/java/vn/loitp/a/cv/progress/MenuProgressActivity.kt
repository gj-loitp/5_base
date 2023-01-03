package vn.loitp.a.cv.progress

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_pb_menu.*
import vn.loitp.R
import vn.loitp.a.cv.progress.circularPb.CircularProgressBarActivity
import vn.loitp.a.cv.progress.circularProgressIndicator.CircularProgressIndicatorActivity
import vn.loitp.a.cv.progress.comparingPerformanceBar.ComparingPerformanceBarActivity
import vn.loitp.a.cv.progress.segmentedPb.StandardExampleActivity
import vn.loitp.a.cv.progress.tileProgressView.TileProgressViewActivity
import vn.loitp.a.cv.progress.window.WindowProgressActivity

@LogTag("MenuProgressActivity")
@IsFullScreen(false)
class MenuProgressActivity : BaseFontActivity() {

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
            this.tvTitle?.text = MenuProgressActivity::class.java.simpleName
        }
        btCircularProgressBar.setSafeOnClickListener {
            launchActivity(CircularProgressBarActivity::class.java)
        }
        btCircularProgressIndicator.setSafeOnClickListener {
            launchActivity(CircularProgressIndicatorActivity::class.java)
        }
        btWindow.setSafeOnClickListener {
            launchActivity(WindowProgressActivity::class.java)
        }
        btSegmentedProgressBar.setSafeOnClickListener {
            launchActivity(StandardExampleActivity::class.java)
        }
        btComparingPerformanceBar.setSafeOnClickListener {
            launchActivity(ComparingPerformanceBarActivity::class.java)
        }
        btTileProgressView.setSafeOnClickListener {
            launchActivity(TileProgressViewActivity::class.java)
        }
    }
}
