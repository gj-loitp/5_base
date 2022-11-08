package vn.loitp.app.activity.customviews.progress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_progress_loading.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.progress.circularProgressBar.CircularProgressBarActivity
import vn.loitp.app.activity.customviews.progress.circularProgressIndicator.CircularProgressIndicatorActivity
import vn.loitp.app.activity.customviews.progress.window.WindowProgressActivity

@LogTag("MenuProgressLoadingViewsActivity")
@IsFullScreen(false)
class MenuProgressLoadingViewsActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_progress_loading
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuProgressLoadingViewsActivity::class.java.simpleName
        }
        btCircularProgressBar.setOnClickListener(this)
        btCircularProgressIndicator.setOnClickListener(this)
        btWindow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btCircularProgressBar -> intent = Intent(this, CircularProgressBarActivity::class.java)
            btCircularProgressIndicator ->
                intent =
                    Intent(this, CircularProgressIndicatorActivity::class.java)
            btWindow -> intent = Intent(this, WindowProgressActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
