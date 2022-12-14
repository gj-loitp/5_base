package vn.loitp.app.activity.customviews.button.circularImageClick

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.button.circularImageClick.LCircularClickImageButton
import kotlinx.android.synthetic.main.activity_circular_image_click.*
import vn.loitp.app.R

@LogTag("CircularImageClickActivity")
@IsFullScreen(false)
class CircularImageClickActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circular_image_click
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
            this.tvTitle?.text = CircularImageClickActivity::class.java.simpleName
        }
        circleButton.setOnCircleClickListener(object :
            LCircularClickImageButton.OnCircleClickListener {
            override fun onClick(v: View?) {
                showShortInformation("onClick")
            }
        })
    }
}
