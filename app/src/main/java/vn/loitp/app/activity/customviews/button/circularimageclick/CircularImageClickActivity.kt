package vn.loitp.app.activity.customviews.button.circularimageclick

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.button.circularimageclick.LCircularClickImageButton
import kotlinx.android.synthetic.main.activity_button_circular_image_click.*
import vn.loitp.app.R

@LogTag("CircularImageClickActivity")
@IsFullScreen(false)
class CircularImageClickActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_circular_image_click
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        circleButton.setOnCircleClickListener(object :
                LCircularClickImageButton.OnCircleClickListener {
                override fun onClick(v: View?) {
                    showShortInformation("onClick")
                }
            })
    }
}
