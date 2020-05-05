package vn.loitp.app.activity.animation.activitytransition

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_animation_2.*
import vn.loitp.app.R

class Animation2Activity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvExit.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_2
    }

    override fun onClick(v: View) {
        when (v) {
            tvExit -> onBackPressed()
        }
    }
}
