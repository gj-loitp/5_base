package vn.loitp.app.a.game.osero

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.activity_top.lActionBar
import vn.loitp.R
import vn.loitp.app.a.game.osero.game.GameOseroActivity
import vn.loitp.app.a.game.osero.model.ai.AINone
import vn.loitp.app.a.game.osero.model.ai.AIStrong
import vn.loitp.app.a.game.osero.model.ai.AIWeak
import vn.loitp.app.a.game.osero.model.ai.OseroAI

@LogTag("TopOseroActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class TopOseroActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_top
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TopOseroActivity::class.java.simpleName
        }
        topModeManualButton.setSafeOnClickListener { startGameActivity(AINone()) }
        topModeAIWeakButton.setSafeOnClickListener { startGameActivity(AIWeak()) }
        topModeAIStrongButton.setSafeOnClickListener { startGameActivity(AIStrong()) }
    }

    private fun startGameActivity(ai: OseroAI) {
        startActivity(GameOseroActivity.createIntent(this, ai))
    }
}
