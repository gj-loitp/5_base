package vn.loitp.app.activity.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_service_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.service.demoservice.DemoServiceActivity
import vn.loitp.app.activity.service.endlessservice.EndlessServiceActivity

@LayoutId(R.layout.activity_service_menu)
@LogTag("MenuServiceActivity")
@IsFullScreen(false)
class MenuServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btDemoService.setOnClickListener(this)
        btEndlessService.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDemoService -> intent = Intent(this, DemoServiceActivity::class.java)
            btEndlessService -> intent = Intent(this, EndlessServiceActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
