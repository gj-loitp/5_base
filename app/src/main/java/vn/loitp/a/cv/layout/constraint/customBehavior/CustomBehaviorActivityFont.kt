package vn.loitp.a.cv.layout.constraint.customBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_custom_behavior.*
import vn.loitp.R

@LogTag("CustomBehaviorActivity")
@IsFullScreen(false)
class CustomBehaviorActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_custom_behavior
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CustomBehaviorActivityFont::class.java.simpleName
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
