package vn.loitp.up.a.game.pong.a

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getSerializableCompat
import com.loitp.core.ext.hideDefaultControls
import vn.loitp.databinding.APongGameBinding
import vn.loitp.up.a.game.pong.pong.Settings

@LogTag("GameActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GameActivity : BaseActivityFont() {
    private lateinit var binding: APongGameBinding

    lateinit var settings: Settings

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        settings =
            intent?.extras?.getSerializableCompat("settings", Settings::class.java) as Settings

        super.onCreate(savedInstanceState)

        binding = APongGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        settings = intent.getSerializableExtra("settings") as Settings
//        LScreenUtil.setScreenOrientation(this, false)
        this.hideDefaultControls()
//        setCustomStatusBar(Color.BLACK, Color.BLACK)
    }

    override fun onRestart() {
        super.onRestart()
        this.onDestroy()
        this.onCreate(null)
    }

    // TODO: Saving game state on activity lifecycle.
    // TODO: Pause the game.
}
