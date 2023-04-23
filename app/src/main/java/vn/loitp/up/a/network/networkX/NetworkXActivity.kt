package vn.loitp.up.a.network.networkX

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import com.rommansabbir.networkx.extension.lastKnownSpeedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import vn.loitp.R
import vn.loitp.databinding.ANetworkXBinding

@LogTag("NetworkXActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class NetworkXActivity : BaseActivityFont() {

    private lateinit var binding: ANetworkXBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANetworkXBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupNetworkX()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/rommansabbir/NetworkX"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = NetworkXActivity::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupNetworkX() {
        NetworkXProvider.isInternetConnectedLiveData.observe(this) {
            binding.textView.text = "Internet connection status: $it"
        }
        NetworkXProvider.lastKnownSpeedLiveData.observe(this) {
            binding.textView2.text =
                "Last Known Speed: Speed - ${it.speed} | Type - ${it.networkTypeNetwork} | Simplified Speed - ${it.simplifiedSpeed}"

        }
    }
}
