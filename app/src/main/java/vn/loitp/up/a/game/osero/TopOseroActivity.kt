package vn.loitp.up.a.game.osero

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AOseroTopBinding
import vn.loitp.up.a.game.osero.game.GameOseroActivity
import vn.loitp.up.a.game.osero.md.ai.AINone
import vn.loitp.up.a.game.osero.md.ai.AIStrong
import vn.loitp.up.a.game.osero.md.ai.AIWeak
import vn.loitp.up.a.game.osero.md.ai.OseroAI

@LogTag("TopOseroActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class TopOseroActivity : BaseActivityFont() {
    private lateinit var binding: AOseroTopBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_osero_top
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AOseroTopBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = TopOseroActivity::class.java.simpleName
        }
        binding.topModeManualButton.setSafeOnClickListener {
            startGameActivity(AINone())
        }
        binding.topModeAIWeakButton.setSafeOnClickListener {
            startGameActivity(AIWeak())
        }
        binding.topModeAIStrongButton.setSafeOnClickListener {
            startGameActivity(AIStrong())
        }
    }

    private fun startGameActivity(ai: OseroAI) {
        startActivity(GameOseroActivity.createIntent(this, ai))
    }
}
