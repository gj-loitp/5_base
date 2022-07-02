package vn.loitp.app.activity.customviews.layout.flowLayout

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LDeviceUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_flow_layout.*
import vn.loitp.app.R

@LogTag("FlowLayoutActivity")
@IsFullScreen(false)
class FlowLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_flow_layout
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FlowLayoutActivity::class.java.simpleName
        }
        for (i in 0..20) {
            val tv = TextView(this)
            tv.text = LDeviceUtil.getRandomString(maxLeng = 15)
            tv.setBackgroundResource(R.drawable.bt_tag)
            flowLayout.addView(tv)
        }
    }
}
