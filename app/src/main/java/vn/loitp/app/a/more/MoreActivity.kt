package vn.loitp.app.a.more

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.helper.more.FrmMore
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_more.*
import vn.loitp.R

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