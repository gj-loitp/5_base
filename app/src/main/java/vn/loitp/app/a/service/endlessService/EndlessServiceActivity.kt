package vn.loitp.app.a.service.endlessService

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_service_endless.*
import vn.loitp.R

@LogTag("EndlessServiceActivity")
@IsFullScreen(false)
class EndlessServiceActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_service_endless
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = EndlessServiceActivity::class.java.simpleName
        }
        btnStartService.setSafeOnClickListener {
            log("START THE FOREGROUND SERVICE ON DEMAND")
            actionOnService(Actions.START)
        }
        btnStopService.setSafeOnClickListener {
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
