package vn.loitp.app.activity.customviews.button.roundedbutton

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.LToast
import kotlinx.android.synthetic.main.activity_rounded_button.*
import loitp.basemaster.R

class RoundedButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_0.setOnClickListener { LToast.showShort(activity, "Click") }
        bt_1.setOnClickListener { LToast.showShort(activity, "Click") }
        bt_2.setOnClickListener { LToast.showShort(activity, "Click") }
        bt_3.setOnClickListener { LToast.showShort(activity, "Click") }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rounded_button
    }
}
