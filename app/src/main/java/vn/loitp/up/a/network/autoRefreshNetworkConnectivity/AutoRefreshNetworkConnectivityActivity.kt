package vn.loitp.up.a.network.autoRefreshNetworkConnectivity

import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener
import vn.loitp.R
import vn.loitp.databinding.AAutoRefreshNetworkConnectivityBinding


@LogTag("AutoRefreshNetworkConnectivityActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class AutoRefreshNetworkConnectivityActivity : BaseActivityFont() {

    private lateinit var binding: AAutoRefreshNetworkConnectivityBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAutoRefreshNetworkConnectivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupConfig()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/nuonveyo/AutoRefreshNetworkConnection"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AutoRefreshNetworkConnectivityActivity::class.java.simpleName
        }
    }

    private fun setupConfig() {
        CheckNetworkConnectionHelper
            .getInstance()
            .registerNetworkChangeListener(object : StopReceiveDisconnectedListener() {
                override fun onDisconnected() {
                    binding.tv.text = getString(R.string.network_disconnected)
                    showShortInformation(getString(R.string.network_disconnected))
                }

                override fun onNetworkConnected() {
                    binding.tv.text = getString(R.string.network_connected)
                    showShortInformation(getString(R.string.network_connected))
                }

                override fun getContext(): Context {
                    return this@AutoRefreshNetworkConnectivityActivity
                }
            })
    }
}
