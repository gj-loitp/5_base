package vn.loitp.app.activity.customviews.button.qbutton

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_q_button.*
import loitp.basemaster.R

class QButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setCornerRadious(5)
        btn.setStrokeWidth(5)
        btn.setStrokeDashGap(5)
        btn.setStrokeDashWidth(90)
        btn.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        btn.setStrokeColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_q_button
    }
}
