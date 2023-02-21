package vn.loitp.up.a.cv.layout.constraint.fabAndSnackbar

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.AFabAndSnackBarBinding

@LogTag("FabAndSnackbarActivity")
@IsFullScreen(false)
class FabAndSnackBarActivity : BaseActivityFont() {

    private lateinit var binding: AFabAndSnackBarBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFabAndSnackBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.showSnackbarButton.setOnClickListener {
            Snackbar.make(
                /* view = */ binding.coordinatorLayout,
                /* text = */ "This is a simple Snackbar",
                /* duration = */ Snackbar.LENGTH_LONG
            )
                .setAction("CLOSE") {
                    showShortInformation("CLOSE")
                }.show()
        }
    }
}
