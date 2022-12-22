package vn.loitp.app.activity.customviews.button.circularImageClick

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.bt.circularImageClick.LCircularClickImageButton
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
