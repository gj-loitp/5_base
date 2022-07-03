package vn.loitp.app.activity.service.demoService

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_service_demo.*
import vn.loitp.app.R

@LogTag("DemoServiceActivity")
@IsFullScreen(false)
class DemoServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btStartService.setOnClickListener(this)
        btStopService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btStartService -> {
                startService(Intent(applicationContext, DemoService::class.java))
            }
            btStopService -> {
                stopService(Intent(applicationContext, DemoService::class.java))
            }
        }
    }
}
