package vn.loitp.app.a.cv.progress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_progress_loading.*
import vn.loitp.R
import vn.loitp.app.a.cv.progress.circularProgressBar.CircularProgressBarActivity
import vn.loitp.app.a.cv.progress.circularProgressIndicator.CircularProgressIndicatorActivity
import vn.loitp.app.a.cv.progress.comparingPerformanceBar.ComparingPerformanceBarActivity
import vn.loitp.app.a.cv.progress.segmentedProgressBar.StandardExampleActivity
import vn.loitp.app.a.cv.progress.window.WindowProgressActivity

@LogTag("MenuProgressLoadingViewsActivity")
@IsFullScreen(false)
class MenuProgressLoadingViewsActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_progress_loading
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
            this.tvTitle?.text = MenuProgressLoadingViewsActivity::class.java.simpleName
        }
        btCircularProgressBar.setOnClickListener(this)
        btCircularProgressIndicator.setOnClickListener(this)
        btWindow.setOnClickListener(this)
        btSegmentedProgressBar.setOnClickListener(this)
        btComparingPerformanceBar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btCircularProgressBar -> intent = Intent(this, CircularProgressBarActivity::class.java)
            btCircularProgressIndicator ->
                intent =
                    Intent(this, CircularProgressIndicatorActivity::class.java)
            btWindow -> intent = Intent(this, WindowProgressActivity::class.java)
            btSegmentedProgressBar -> intent = Intent(this, StandardExampleActivity::class.java)
            btComparingPerformanceBar -> intent =
                Intent(this, ComparingPerformanceBarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
