package vn.loitp.activity.anim.basicTransitionFrm

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_basic_transition.*
import vn.loitp.app.R

// https://github.com/googlesamples/android-BasicTransition/#readme
@LogTag("BasicTransitionActivity")
@IsFullScreen(false)
class BasicTransitionActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_basic_transition
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = BasicTransitionFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }

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
            this.tvTitle?.text = BasicTransitionActivity::class.java.simpleName
        }
    }
}
