package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.imageview.fidgetspinner.LFidgetSpinner

import loitp.basemaster.R

class FidgetSpinnerImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val f = findViewById<LFidgetSpinner>(R.id.fidgetspinner)
        f.setImageDrawable(R.drawable.spinner)
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
