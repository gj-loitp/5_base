package vn.loitp.app.activity.animation.basictransitionfrm

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/googlesamples/android-BasicTransition/#readme

@LayoutId(R.layout.activity_basic_transition)
@LogTag("BasicTransitionActivity")
@IsFullScreen(false)
class BasicTransitionActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = BasicTransitionFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }
    }

}
