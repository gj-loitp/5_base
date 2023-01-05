package vn.loitp.a.anim.activityTransition

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.a_animation_2.*
import vn.loitp.R

@LogTag("Animation2Activity")
@IsFullScreen(false)
class Animation2ActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        tvExit.setSafeOnClickListener {
            onBaseBackPressed()
        }
    }
}
