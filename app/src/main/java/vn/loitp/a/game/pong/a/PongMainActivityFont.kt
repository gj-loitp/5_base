package vn.loitp.a.game.pong.a

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LScreenUtil
import vn.loitp.R
import vn.loitp.a.game.pong.pong.Settings

@LogTag("PongMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PongMainActivityFont : BaseActivityFont() {

    private var settings = Settings()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_pong_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        LScreenUtil.setScreenOrientation(this, false)
        LScreenUtil.hideDefaultControls(this)
//        setCustomStatusBar(Color.BLACK, Color.BLACK)
    }

    @Suppress("unused")
    fun settingsActivity(view: View) {
        Intent(this, SettingsActivityFont::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(it)
        }
    }

    @Suppress("unused")
    fun play(view: View) {
        val intent = Intent(this, GameActivityFont::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }

    // TODO: Settings (turn off sounds etc.)
}
