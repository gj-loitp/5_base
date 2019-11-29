package vn.loitp.app.activity.customviews.layout.roundablelayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import loitp.basemaster.R

//TODO https://github.com/zladnrms/RoundableLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7934
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
