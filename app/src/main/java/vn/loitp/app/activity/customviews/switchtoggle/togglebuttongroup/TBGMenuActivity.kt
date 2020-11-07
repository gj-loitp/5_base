package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_switch_tbg_menu.*
import vn.loitp.app.R

//https://github.com/nex3z/ToggleButtonGroup
@LogTag("TBGMenuActivity")
@IsFullScreen(false)
class TBGMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        btnMultiSelectSample.setOnClickListener {
            val intent = Intent(this, TBGMultiSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btnSingleSelectSample.setOnClickListener {
            val intent = Intent(this, TBGSingleSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btnLabelSample.setOnClickListener {
            val intent = Intent(this, TBGFlowLabelActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btnCustomButtonSample.setOnClickListener {
            val intent = Intent(this, TBGCustomButtonActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
