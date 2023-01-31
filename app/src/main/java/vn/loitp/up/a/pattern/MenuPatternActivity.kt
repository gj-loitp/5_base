package vn.loitp.up.a.pattern

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.pattern.mvp.MVPActivityFont
import vn.loitp.a.pattern.mvvm.MVVMActivityFont
import vn.loitp.databinding.APatternMenuBinding
import vn.loitp.up.a.pattern.observer.ObserverPatternActivity

@LogTag("MenuPatternActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuPatternActivity : BaseActivityFont() {

    private lateinit var binding: APatternMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APatternMenuBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuPatternActivity::class.java.simpleName
        }
        binding.btObserver.setSafeOnClickListener {
            launchActivity(ObserverPatternActivity::class.java)
        }
        binding.btMVVM.setSafeOnClickListener {
            launchActivity(MVVMActivityFont::class.java)
        }
        binding.btMVP.setSafeOnClickListener {
            launchActivity(MVPActivityFont::class.java)
        }
    }
}
