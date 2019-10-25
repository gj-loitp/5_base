package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.progressloadingview.circular.LCircularProgressBar

import loitp.basemaster.R

class CircularProgressBarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressBar = findViewById<LCircularProgressBar>(R.id.progress_bar)
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
        return R.layout.activity_circular_progress_bar
    }
}
