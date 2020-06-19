package vn.loitp.app.activity.service.endlessservice

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_service_endless.*
import vn.loitp.app.R

class EndlessServiceActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_endless
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnStartService.setOnClickListener {
            log("START THE FOREGROUND SERVICE ON DEMAND")
            actionOnService(Actions.START)
        }
        btnStopService.setOnClickListener {
            log("STOP THE FOREGROUND SERVICE ON DEMAND")
            actionOnService(Actions.STOP)
        }
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >= 26 SDK_INT")
                startForegroundService(it)
            } else {
                log("Starting the service in < 26 SDK_INT")
                startService(it)
            }
        }
    }
}
