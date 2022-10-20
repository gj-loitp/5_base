package vn.loitp.app.activity.game.osero

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.activity_top.lActionBar
import vn.loitp.app.R
import vn.loitp.app.activity.game.osero.game.GameOseroActivity
import vn.loitp.app.activity.game.osero.model.ai.AINone
import vn.loitp.app.activity.game.osero.model.ai.AIStrong
import vn.loitp.app.activity.game.osero.model.ai.AIWeak
import vn.loitp.app.activity.game.osero.model.ai.OseroAI

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
            this.viewShadow?.isVisible = true
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