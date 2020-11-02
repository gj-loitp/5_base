package vn.loitp.app.activity.customviews.button.qbutton

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import kotlinx.android.synthetic.main.activity_button_q.*
import vn.loitp.app.R

@LogTag("QButtonActivity")
@IsFullScreen(false)
class QButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_q
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setCornerRadious(5)
        btn.setStrokeWidth(5)
        btn.setStrokeDashGap(5)
        btn.setStrokeDashWidth(90)
        btn.setBackgroundColor(LAppResource.getColor(R.color.colorPrimary))
        btn.setStrokeColor(LAppResource.getColor(R.color.colorPrimaryDark))
    }
}
