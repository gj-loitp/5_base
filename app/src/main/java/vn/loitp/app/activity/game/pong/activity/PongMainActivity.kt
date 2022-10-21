package vn.loitp.app.activity.game.pong.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import vn.loitp.app.R
import vn.loitp.app.activity.game.pong.pong.Settings

@LogTag("PongMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PongMainActivity : BaseFontActivity() {

    private var settings = Settings()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pong_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        LScreenUtil.setScreenOrientation(this, false)
        LScreenUtil.hideDefaultControls(this)
//        setCustomStatusBar(Color.BLACK, Color.BLACK)
    }

    fun settingsActivity(view: View) {
        Intent(this, SettingsActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(it)
        }
    }

    fun play(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }

    // TODO: Settings (turn off sounds etc.)
}
