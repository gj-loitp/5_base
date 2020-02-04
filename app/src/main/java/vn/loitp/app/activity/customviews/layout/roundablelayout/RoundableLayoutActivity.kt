package vn.loitp.app.activity.customviews.layout.roundablelayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

class RoundableLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_roundable_layout
    }
}
