package vn.loitp.app.activity.animation.activityTransition

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_animation_2.*
import vn.loitp.app.R

@LogTag("Animation2Activity")
@IsFullScreen(false)
class Animation2Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_2
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
