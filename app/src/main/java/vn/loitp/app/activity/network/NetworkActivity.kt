package vn.loitp.app.activity.network

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LConnectivityUtil
import com.data.EventBusData
import kotlinx.android.synthetic.main.frm_text.*
import vn.loitp.app.R

class NetworkActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStatus(LConnectivityUtil.isConnected(activity))
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
        logD("onNetworkChange: " + event.isConnected)
        showStatus(event.isConnected)
    }

    private fun showStatus(isConnected: Boolean) {
        textView.text = "isConnected: $isConnected"
    }
}
