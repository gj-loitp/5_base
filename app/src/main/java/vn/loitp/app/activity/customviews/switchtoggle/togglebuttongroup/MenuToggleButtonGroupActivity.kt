package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.content.Intent
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_toggle_button_group.*
import vn.loitp.app.R

class MenuToggleButtonGroupActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_toggle_button_group
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnMultiSelectSample.setOnClickListener {
            val intent = Intent(activity, MultiSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnSingleSelectSample.setOnClickListener {
            val intent = Intent(activity, SingleSelectActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnLabelSample.setOnClickListener {
            val intent = Intent(activity, FlowLabelActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btnCustomButtonSample.setOnClickListener {
            val intent = Intent(activity, CustomButtonActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
