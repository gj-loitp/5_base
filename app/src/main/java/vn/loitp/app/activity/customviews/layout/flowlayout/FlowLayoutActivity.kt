package vn.loitp.app.activity.customviews.layout.flowlayout

import android.os.Bundle
import android.widget.TextView
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import kotlinx.android.synthetic.main.activity_flow_layout.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_flow_layout)
@LogTag("FlowLayoutActivity")
@IsFullScreen(false)
class FlowLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        for (i in 0..20) {
            val tv = TextView(this)
            tv.text = LDeviceUtil.getRandomString(maxLeng = 15)
            tv.setBackgroundResource(R.drawable.bt_tag)
            flowLayout.addView(tv)
        }
    }

}
