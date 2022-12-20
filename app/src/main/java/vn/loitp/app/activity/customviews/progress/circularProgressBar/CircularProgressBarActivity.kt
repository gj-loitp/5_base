package vn.loitp.app.activity.customviews.progress.circularProgressBar

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
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
