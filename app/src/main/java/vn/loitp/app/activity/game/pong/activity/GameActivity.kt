package vn.loitp.app.activity.game.pong.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.loitp.app.R
import vn.loitp.app.activity.game.pong.pong.Settings

class GameActivity : AppCompatActivity() {

    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = intent.getSerializableExtra("settings") as Settings
        setContentView(R.layout.activity_game)
    }

    override fun onRestart() {
        super.onRestart()
        this.onDestroy()
        this.onCreate(null)
    }

    // TODO: Saving game state on activity lifecycle.
    // TODO: Pause the game.
}
