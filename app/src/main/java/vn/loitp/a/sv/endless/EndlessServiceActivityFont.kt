package vn.loitp.a.sv.endless

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sv_endless.*
import vn.loitp.R

@LogTag("EndlessServiceActivity")
@IsFullScreen(false)
class EndlessServiceActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sv_endless
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = EndlessServiceActivityFont::class.java.simpleName
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
