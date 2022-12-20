package vn.loitp.app.activity.customviews.layout.constraintLayout.customBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_custom_behavior.*
import vn.loitp.app.R

@LogTag("CustomBehaviorActivity")
@IsFullScreen(false)
class CustomBehaviorActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_behavior
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
            this.tvTitle?.text = CustomBehaviorActivity::class.java.simpleName
        }
        fab.setSafeOnClickListener {
            Snackbar.make(
                coordinatorLayout,
                "This is a simple Snackbar", Snackbar.LENGTH_LONG
            )
                .setAction("CLOSE") {
                    // do sth
                }.show()
        }
    }
}
