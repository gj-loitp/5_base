package vn.loitp.up.a.network

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.AMenuNetworkBinding
import vn.loitp.up.a.network.autoRefreshNetworkConnectivity.AutoRefreshNetworkConnectivityActivity
import vn.loitp.up.a.network.network.NetworkActivity
import vn.loitp.up.a.network.networkX.NetworkXActivity

@LogTag("MenuNetworkActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
@IsKeepScreenOn(true)
class MenuNetworkActivity : BaseActivityFont() {

    private lateinit var binding: AMenuNetworkBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuNetworkActivity::class.java.simpleName
        }
        binding.btNetwork.setSafeOnClickListener {
            launchActivity(NetworkActivity::class.java)
        }
        binding.btAutoRefreshNetWorkConnectivity.setSafeOnClickListener {
            launchActivity(AutoRefreshNetworkConnectivityActivity::class.java)
        }
        binding.btNetworkX.setSafeOnClickListener {
            launchActivity(NetworkXActivity::class.java)
        }
    }
}
