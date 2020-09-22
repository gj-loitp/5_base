package vn.loitp.app.activity.customviews.progressloadingview.window

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_progress_window.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_progress_window)
@LogTag("WindowProgressActivity")
@IsFullScreen(false)
class WindowProgressActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
