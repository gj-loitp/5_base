package vn.loitp.up.a.game.puzzle

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.APuzzleBoardOptionsBinding
import vn.loitp.up.a.game.puzzle.ui.options.BoardOptionsFragment

@LogTag("BoardOptionsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class BoardOptionsActivity : BaseActivityFont() {

    private lateinit var binding: APuzzleBoardOptionsBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APuzzleBoardOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    /* containerViewId = */ R.id.container,
                    /* fragment = */ BoardOptionsFragment.newInstance()
                ).commitNow()
        }
    }

}
