package vn.loitp.app.activity.game.osero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_top.*
import vn.loitp.app.R
import vn.loitp.app.activity.game.osero.game.GameActivity
import vn.loitp.app.activity.game.osero.model.ai.AINone
import vn.loitp.app.activity.game.osero.model.ai.AIStrong
import vn.loitp.app.activity.game.osero.model.ai.AIWeak
import vn.loitp.app.activity.game.osero.model.ai.OseroAI

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        topModeManualButton.setOnClickListener { startGameActivity(AINone()) }
        topModeAIWeakButton.setOnClickListener { startGameActivity(AIWeak()) }
        topModeAIStrongButton.setOnClickListener { startGameActivity(AIStrong()) }
    }

    private fun startGameActivity(ai: OseroAI) {
        startActivity(GameActivity.createIntent(this, ai))
    }
}
