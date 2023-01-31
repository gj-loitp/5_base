package vn.loitp.up.a.game

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.game.findNumber.ui.SplashActivity
import vn.loitp.R
import vn.loitp.a.game.osero.TopOseroActivityFont
import vn.loitp.databinding.AGameMenuBinding
import vn.loitp.up.a.game.pong.a.PongMainActivity
import vn.loitp.up.a.game.puzzle.BoardOptionsActivity

@LogTag("MenuGameActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuGameActivity : BaseActivityFont() {

    private lateinit var binding: AGameMenuBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_game_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AGameMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuGameActivity::class.java.simpleName
        }

        binding.btFindNumber.setSafeOnClickListener {
            launchActivity(SplashActivity::class.java)
        }
        binding.btOsero.setSafeOnClickListener {
            launchActivity(TopOseroActivityFont::class.java)
        }
        binding.btPong.setSafeOnClickListener {
            launchActivity(PongMainActivity::class.java)
        }
        binding.btPuzzle.setSafeOnClickListener {
            launchActivity(BoardOptionsActivity::class.java)
        }

    }
}
