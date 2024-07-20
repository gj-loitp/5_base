package vn.loitp.up.a.game.pong.a

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideDefaultControls
import vn.loitp.databinding.APongMainBinding
import vn.loitp.up.a.game.pong.pong.Settings

@LogTag("PongMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PongMainActivity : BaseActivityFont() {
    private lateinit var binding: APongMainBinding
    private var settings = Settings()

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APongMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.hideDefaultControls()
    }

    @Suppress("unused")
    fun settingsActivity(view: View) {
        Intent(this, SettingsActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(it)
        }
    }

    @Suppress("unused")
    fun play(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }

    // TODO: Settings (turn off sounds etc.)
}
