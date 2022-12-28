package vn.loitp.a.cv.layout.flow

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LDeviceUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_flow_layout.*
import vn.loitp.R

@LogTag("FlowLayoutActivity")
@IsFullScreen(false)
class FlowLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_flow_layout
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
            this.tvTitle?.text = FlowLayoutActivity::class.java.simpleName
        }
        for (i in 0..20) {
            val tv = TextView(this)
            tv.text = LDeviceUtil.getRandomString(maxLength = 15)
            tv.setBackgroundResource(R.drawable.bt_tag)
            flowLayout.addView(tv)
        }
    }
}
