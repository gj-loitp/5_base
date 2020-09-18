package vn.loitp.app.activity.network

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LConnectivityUtil
import com.data.EventBusData
import kotlinx.android.synthetic.main.frm_text.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_network)
@LogTag("NetworkActivity")
class NetworkActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStatus(LConnectivityUtil.isConnected(activity))
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onNetworkChange(event: EventBusData.ConnectEvent) {
        super.onNetworkChange(event)
        logD("onNetworkChange: " + event.isConnected)
        showStatus(event.isConnected)
    }

    @SuppressLint("SetTextI18n")
    private fun showStatus(isConnected: Boolean) {
        textView.text = "isConnected: $isConnected"
    }
}
