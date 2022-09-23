package vn.loitp.app.activity.more

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.helper.more.FrmMore
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_more.*
import vn.loitp.app.R

@LogTag("MoreActivity")
@IsFullScreen(false)
class MoreActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_more
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MoreActivity::class.java.simpleName
        }
        LScreenUtil.addFragment(
            activity = this,
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = FrmMore(),
            isAddToBackStack = false
        )
    }
}
