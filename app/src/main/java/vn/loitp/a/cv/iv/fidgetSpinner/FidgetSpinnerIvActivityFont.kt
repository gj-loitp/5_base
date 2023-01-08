package vn.loitp.a.cv.iv.fidgetSpinner

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_iv_fidget_spinner.*
import vn.loitp.R

@LogTag("FidgetSpinnerIvActivity")
@IsFullScreen(false)
class FidgetSpinnerIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_fidget_spinner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FidgetSpinnerIvActivityFont::class.java.simpleName
        }
        fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }
}
