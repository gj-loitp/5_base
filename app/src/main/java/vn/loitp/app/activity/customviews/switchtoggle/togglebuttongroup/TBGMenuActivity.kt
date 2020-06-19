package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.content.Intent
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_switch_tbg_menu.*
import vn.loitp.app.R

//https://github.com/nex3z/ToggleButtonGroup
class TBGMenuActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnMultiSelectSample.setOnClickListener {
            val intent = Intent(activity, TBGMultiSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnSingleSelectSample.setOnClickListener {
            val intent = Intent(activity, TBGSingleSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnLabelSample.setOnClickListener {
            val intent = Intent(activity, TBGFlowLabelActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnCustomButtonSample.setOnClickListener {
            val intent = Intent(activity, TBGCustomButtonActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
