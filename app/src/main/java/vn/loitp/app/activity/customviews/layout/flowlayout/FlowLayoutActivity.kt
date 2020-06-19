package vn.loitp.app.activity.customviews.layout.flowlayout

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import kotlinx.android.synthetic.main.activity_flow_layout.*
import vn.loitp.app.R

class FlowLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_flow_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (i in 0..50) {
            val tv = TextView(activity)
            tv.text = LDeviceUtil.getRandomString(15)
            tv.setBackgroundResource(R.drawable.bt_tag)
            flowLayout.addView(tv)
        }
    }

}
