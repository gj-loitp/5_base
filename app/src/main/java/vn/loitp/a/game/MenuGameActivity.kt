package vn.loitp.a.game

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.game.findNumber.ui.SplashActivity
import kotlinx.android.synthetic.main.a_game_menu.*
import vn.loitp.R
import vn.loitp.a.game.osero.TopOseroActivity
import vn.loitp.a.game.pong.a.PongMainActivity
import vn.loitp.a.game.puzzle.BoardOptionsActivity

@LogTag("MenuGameActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuGameActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_game_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
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
            btOsero -> launchActivity(TopOseroActivity::class.java)
            btPong -> launchActivity(PongMainActivity::class.java)
            btPuzzle -> launchActivity(BoardOptionsActivity::class.java)
        }
    }
}
