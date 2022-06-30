package vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
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
            Snackbar.make(
                coordinatorLayout,
                "This is a simple Snackbar",
                Snackbar.LENGTH_LONG
            )
                .setAction("CLOSE") {
                    showShortInformation("CLOSE")
                }.show()
        }
    }
}
