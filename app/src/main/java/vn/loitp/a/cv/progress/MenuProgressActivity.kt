package vn.loitp.a.cv.progress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_pb_menu.*
import vn.loitp.R
import vn.loitp.a.cv.progress.circularPb.CircularProgressBarActivity
import vn.loitp.a.cv.progress.circularProgressIndicator.CircularProgressIndicatorActivity
import vn.loitp.a.cv.progress.comparingPerformanceBar.ComparingPerformanceBarActivity
import vn.loitp.a.cv.progress.segmentedPb.StandardExampleActivity
import vn.loitp.a.cv.progress.window.WindowProgressActivity

@LogTag("MenuProgressActivity")
@IsFullScreen(false)
class MenuProgressActivity : BaseFontActivity(), View.OnClickListener {

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
