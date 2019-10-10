package vn.loitp.app.activity.customviews.progressloadingview

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_progress_loading.*

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.progressloadingview.avloadingindicatorview.AVLoadingIndicatorActivity
import vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar.CircularProgressBarActivity
import vn.loitp.app.activity.customviews.progressloadingview.circularprogressindicator.CircularProgressIndicatorActivity
import vn.loitp.app.activity.customviews.progressloadingview.window.WindowProgressActivity

class MenuProgressLoadingViewsActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btAvloadingIndicatorView.setOnClickListener(this)
        btCircularProgressBar.setOnClickListener(this)
        btCircularProgressIndicator.setOnClickListener(this)
        btWindow.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_progress_loading
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btAvloadingIndicatorView -> intent = Intent(activity, AVLoadingIndicatorActivity::class.java)
            btCircularProgressBar -> intent = Intent(activity, CircularProgressBarActivity::class.java)
            btCircularProgressIndicator -> intent = Intent(activity, CircularProgressIndicatorActivity::class.java)
            btWindow -> intent = Intent(activity, WindowProgressActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
