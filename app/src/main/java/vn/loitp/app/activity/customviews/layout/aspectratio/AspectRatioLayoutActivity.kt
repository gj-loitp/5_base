package vn.loitp.app.activity.customviews.layout.aspectratio

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

@LogTag("AspectRatioLayoutActivity")
@IsFullScreen(false)
class AspectRatioLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_aspect_ratio_layout
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
            this.tvTitle?.text = AspectRatioLayoutActivity::class.java.simpleName
        }
    }
}
