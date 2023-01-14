package vn.loitp.a.game

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.game.findNumber.ui.SplashActivity
import kotlinx.android.synthetic.main.a_game_menu.*
import vn.loitp.R
import vn.loitp.a.game.osero.TopOseroActivityFont
import vn.loitp.a.game.pong.a.PongMainActivityFont
import vn.loitp.a.game.puzzle.BoardOptionsActivityFont

@LogTag("MenuGameActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuGameActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_game_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuGameActivity::class.java.simpleName
        }

        btFindNumber.setOnClickListener(this)
        btOsero.setOnClickListener(this)
        btPong.setOnClickListener(this)
        btPuzzle.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v) {
            btFindNumber -> launchActivity(SplashActivity::class.java)
            btOsero -> launchActivity(TopOseroActivityFont::class.java)
            btPong -> launchActivity(PongMainActivityFont::class.java)
            btPuzzle -> launchActivity(BoardOptionsActivityFont::class.java)
        }
    }
}
