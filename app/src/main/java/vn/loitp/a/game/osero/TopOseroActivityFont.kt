package vn.loitp.a.game.osero

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_osero_top.*
import vn.loitp.R
import vn.loitp.a.game.osero.game.GameOseroActivityFont
import vn.loitp.a.game.osero.md.ai.AINone
import vn.loitp.a.game.osero.md.ai.AIStrong
import vn.loitp.a.game.osero.md.ai.AIWeak
import vn.loitp.a.game.osero.md.ai.OseroAI

@LogTag("TopOseroActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class TopOseroActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_osero_top
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
            this.tvTitle?.text = TopOseroActivityFont::class.java.simpleName
        }
        topModeManualButton.setSafeOnClickListener { startGameActivity(AINone()) }
        topModeAIWeakButton.setSafeOnClickListener { startGameActivity(AIWeak()) }
        topModeAIStrongButton.setSafeOnClickListener { startGameActivity(AIStrong()) }
    }

    private fun startGameActivity(ai: OseroAI) {
        startActivity(GameOseroActivityFont.createIntent(this, ai))
    }
}
