package vn.loitp.app.activity.game.pong.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vn.loitp.app.R
import vn.loitp.app.activity.game.pong.pong.Settings

class MainActivity : AppCompatActivity() {

    private var settings = Settings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
