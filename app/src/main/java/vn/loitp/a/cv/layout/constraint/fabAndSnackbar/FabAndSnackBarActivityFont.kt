package vn.loitp.a.cv.layout.constraint.fabAndSnackbar

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import kotlinx.android.synthetic.main.a_fab_and_snack_bar.*
import vn.loitp.R

@LogTag("FabAndSnackbarActivity")
@IsFullScreen(false)
class FabAndSnackBarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fab_and_snack_bar
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
