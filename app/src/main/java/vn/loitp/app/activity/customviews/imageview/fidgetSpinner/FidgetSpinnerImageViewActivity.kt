package vn.loitp.app.activity.customviews.imageview.fidgetSpinner

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fidget_spinner_image_view.*
import vn.loitp.app.R

@LogTag("FidgetSpinnerImageViewActivity")
@IsFullScreen(false)
class FidgetSpinnerImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fidget_spinner_image_view
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
            this.tvTitle?.text = FidgetSpinnerImageViewActivity::class.java.simpleName
        }
        fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }
}
