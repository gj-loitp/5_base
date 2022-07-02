package vn.loitp.app.activity.customviews.progressLoadingView.window

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_progress_window.*
import vn.loitp.app.R

@LogTag("WindowProgressActivity")
@IsFullScreen(false)
class WindowProgressActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_progress_window
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        wp10progressBar.setIndicatorRadius(5)
        showWP7Btn.setOnClickListener {
            wp7progressBar.showProgressBar()
        }
        hideWP7Btn.setOnClickListener {
            wp7progressBar.hideProgressBar()
        }
        showWP10Btn.setOnClickListener {
            wp10progressBar.showProgressBar()
        }
        hideWP10Btn.setOnClickListener {
            wp10progressBar.hideProgressBar()
        }
    }
}
