package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_interview_vn_iq.*
import vn.loitp.app.R

@LogTag("InterviewVNIQActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class InterviewVNIQActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_interview_vn_iq
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = InterviewVNIQActivity::class.java.simpleName
        }

        LScreenUtil.addFragment(
            activity = this,
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = FrmListPackage(),
            isAddToBackStack = true
        )
    }
}
