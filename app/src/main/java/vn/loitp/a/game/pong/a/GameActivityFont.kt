package vn.loitp.a.game.pong.a

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.hideDefaultControls
import vn.loitp.R
import vn.loitp.a.game.pong.pong.Settings

@LogTag("GameActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GameActivityFont : BaseActivityFont() {

    lateinit var settings: Settings

    override fun setLayoutResourceId(): Int {
        return R.layout.a_pong_game
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        settings = intent.getSerializableExtra("settings") as Settings
        super.onCreate(savedInstanceState)

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
