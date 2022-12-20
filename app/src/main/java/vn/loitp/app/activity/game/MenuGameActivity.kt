package vn.loitp.app.activity.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu.lActionBar
import kotlinx.android.synthetic.main.activity_menu_game.*
import vn.loitp.app.R
import vn.loitp.app.activity.game.osero.TopOseroActivity
import vn.loitp.app.activity.game.pong.activity.PongMainActivity
import vn.loitp.app.activity.game.puzzle.BoardOptionsActivity

@LogTag("MenuGameActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuGameActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_game
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

        btOsero.setOnClickListener(this)
        btPong.setOnClickListener(this)
        btPuzzle.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val intent = when (v) {
            btOsero -> {
                Intent(this, TopOseroActivity::class.java)
            }
            btPong -> {
                Intent(this, PongMainActivity::class.java)
            }
            btPuzzle -> {
                Intent(this, BoardOptionsActivity::class.java)
            }
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
