package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_progress_circular_progress_bar.*
import vn.loitp.app.R

@LogTag("CircularProgressBarActivity")
@IsFullScreen(false)
class CircularProgressBarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_progress_circular_progress_bar
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = CircularProgressBarActivity::class.java.simpleName
        }

        progressBar.progress = 30f
        progressBar.configure()
            .animateProgress(true)
            .maximum(40f)
            .progress(30f)
            .apply()
    }
}
