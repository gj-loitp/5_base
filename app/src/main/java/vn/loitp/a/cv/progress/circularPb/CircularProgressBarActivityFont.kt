package vn.loitp.a.cv.progress.circularPb

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_progress_circular_progress_bar.*
import vn.loitp.R

@LogTag("CircularProgressBarActivity")
@IsFullScreen(false)
class CircularProgressBarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_progress_circular_progress_bar
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
            this.tvTitle?.text = CircularProgressBarActivityFont::class.java.simpleName
        }

        progressBar.progress = 30f
        progressBar.configure()
            .animateProgress(true)
            .maximum(40f)
            .progress(30f)
            .apply()
    }
}