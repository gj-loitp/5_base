package vn.loitp.a.cv.bt.circularImageClick

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.bt.circularImageClick.LCircularClickImageButton
import kotlinx.android.synthetic.main.a_circular_image_click.*
import vn.loitp.R

@LogTag("CircularImageClickActivity")
@IsFullScreen(false)
class CircularImageClickActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_circular_image_click
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CircularImageClickActivityFont::class.java.simpleName
        }
        circleButton.setOnCircleClickListener(object :
            LCircularClickImageButton.OnCircleClickListener {
            override fun onClick(v: View?) {
                showShortInformation("onClick")
            }
        })
    }
}
