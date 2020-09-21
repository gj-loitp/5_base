package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_progress_circular_progress_bar.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_progress_circular_progress_bar)
@LogTag("CircularProgressBarActivity")
@IsFullScreen(false)
class CircularProgressBarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar.progress = 30f
        progressBar.configure().animateProgress(true).maximum(40f).progress(30f).apply()
    }

}
