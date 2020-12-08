package vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fab_and_snackbar.*
import vn.loitp.app.R

@LogTag("FabAndSnackbarActivity")
@IsFullScreen(false)
class FabAndSnackBarActivity : BaseFontActivity() {


    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fab_and_snackbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        showSnackbarButton.setOnClickListener {
            Snackbar.make(coordinatorLayout,
                    "This is a simple Snackbar",
                    Snackbar.LENGTH_LONG)
                    .setAction("CLOSE") {
                        showShortInformation("CLOSE")
                    }.show()
        }
    }
}
