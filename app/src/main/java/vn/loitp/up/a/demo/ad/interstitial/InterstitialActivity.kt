package vn.loitp.up.a.demo.ad.interstitial

import android.os.Bundle
import com.google.android.gms.ads.*
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdInterstitialBinding

@LogTag("InterstitialActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class InterstitialActivity : BaseActivityFont() {
    private lateinit var binding: AAdInterstitialBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdInterstitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = InterstitialActivity::class.java.simpleName
        }
        binding.btShow.setSafeOnClickListener {

        }
    }
}
