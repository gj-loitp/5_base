package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
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

        fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }
}
