package vn.loitp.a.game.puzzle

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import vn.loitp.R
import vn.loitp.a.game.puzzle.ui.options.BoardOptionsFragment

@LogTag("BoardOptionsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class BoardOptionsActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_puzzle_board_options
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BoardOptionsFragment.newInstance()).commitNow()
        }
    }

}
