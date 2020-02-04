package vn.loitp.app.activity.service.demoservice

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_service_demo.*
import vn.loitp.app.R

class DemoServiceActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btStartService.setOnClickListener(this)
        btStopService.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_demo
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btStartService -> {
                startService(Intent(applicationContext, DemoService::class.java))
            }
            R.id.btStopService -> {
                stopService(Intent(applicationContext, DemoService::class.java))
            }
        }
    }
}
