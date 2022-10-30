package vn.loitp.app.activity.game.pong.activity

import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import vn.loitp.app.R
import vn.loitp.app.activity.game.pong.pong.Settings

@LogTag("GameActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GameActivity : BaseFontActivity() {

    lateinit var settings: Settings

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pong_game
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        settings = intent.getSerializableExtra("settings") as Settings
        super.onCreate(savedInstanceState)

//        settings = intent.getSerializableExtra("settings") as Settings
//        LScreenUtil.setScreenOrientation(this, false)
        LScreenUtil.hideDefaultControls(this)
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