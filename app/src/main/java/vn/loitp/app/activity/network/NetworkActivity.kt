package vn.loitp.app.activity.network

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.data.EventBusData
import kotlinx.android.synthetic.main.frm_text.*
import vn.loitp.app.R

class NetworkActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_network
    }

    override fun onNetworkChange(event: EventBusData.ConnectEvent) {
        super.onNetworkChange(event)
        LLog.d(TAG, "onNetworkChange: " + event.isConnected)
        val networkInfo = "isConnected ${event.isConnected}\nisConnectedFast: ${event.isConnectedFast}\n" +
                "isConnectedMobile: ${event.isConnectedMobile}\nisConnectedWifi: ${event.isConnectedWifi}"
        tv.text = networkInfo
    }
}
