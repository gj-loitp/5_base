package vn.loitp.app.activity.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_service_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.service.demoservice.DemoServiceActivity
import vn.loitp.app.activity.service.endlessservice.EndlessServiceActivity

class MenuServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btDemoService.setOnClickListener(this)
        btEndlessService.setOnClickListener(this)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.btDemoService -> intent = Intent(activity, DemoServiceActivity::class.java)
            R.id.btEndlessService -> intent = Intent(activity, EndlessServiceActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
