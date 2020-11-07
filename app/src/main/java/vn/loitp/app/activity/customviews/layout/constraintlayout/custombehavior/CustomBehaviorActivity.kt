package vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.snackbar.Snackbar
import com.views.setSafeOnClickListener
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

        fab.setSafeOnClickListener {
            Snackbar.make(coordinatorLayout,
                    "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE") { v ->
                        //do sth
                    }.show()
        }
    }

}
