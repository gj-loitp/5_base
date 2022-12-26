package vn.loitp.a.cv.progress.window

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.a_progress_window.*
import vn.loitp.R

@LogTag("WindowProgressActivity")
@IsFullScreen(false)
class WindowProgressActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_progress_window
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
