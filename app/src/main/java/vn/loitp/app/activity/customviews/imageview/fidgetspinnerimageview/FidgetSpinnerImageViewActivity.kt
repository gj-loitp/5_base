package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_fidgetspinner_imageview.*
import vn.loitp.app.R

class FidgetSpinnerImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fidgetspinner_imageview
    }
}
