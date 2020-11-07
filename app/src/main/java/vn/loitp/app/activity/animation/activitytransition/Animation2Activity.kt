package vn.loitp.app.activity.animation.activitytransition

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_animation_2.*
import vn.loitp.app.R

@LogTag("Animation2Activity")
@IsFullScreen(false)
class Animation2Activity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        tvExit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            tvExit -> onBackPressed()
        }
    }
}
