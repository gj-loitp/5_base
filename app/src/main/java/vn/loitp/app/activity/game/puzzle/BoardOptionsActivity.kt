package vn.loitp.app.activity.game.puzzle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.loitp.app.R
import vn.loitp.app.activity.game.puzzle.ui.boardoptions.BoardOptionsFragment

class BoardOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_options_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BoardOptionsFragment.newInstance())
                .commitNow()
        }
    }

}
