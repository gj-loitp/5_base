package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_progress_circular_progress_bar.*
import vn.loitp.app.R

class CircularProgressBarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar.progress = 30f
        progressBar.configure().animateProgress(true).maximum(40f).progress(30f).apply()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_progress_circular_progress_bar
    }
}
