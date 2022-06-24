package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_imageview_fidget_spinner.*
import vn.loitp.app.R

@LogTag("FidgetSpinnerImageViewActivity")
@IsFullScreen(false)
class FidgetSpinnerImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_fidget_spinner
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FidgetSpinnerImageViewActivity::class.java.simpleName
        }
        fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }
}
