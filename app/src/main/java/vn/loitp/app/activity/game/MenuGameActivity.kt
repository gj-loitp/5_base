package vn.loitp.app.activity.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu.lActionBar
import kotlinx.android.synthetic.main.activity_menu_game.*
import vn.loitp.app.R
import vn.loitp.app.activity.game.osero.TopOseroActivity
import vn.loitp.app.activity.game.pong.activity.MainActivity

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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuGameActivity::class.java.simpleName
        }

        btOsero.setOnClickListener(this)
        btPong.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val intent: Intent? =
            when (v) {
                btOsero -> {
                    Intent(this, TopOseroActivity::class.java)
                }
                btPong -> {
                    Intent(this, MainActivity::class.java)
                }
                else -> null
            }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
