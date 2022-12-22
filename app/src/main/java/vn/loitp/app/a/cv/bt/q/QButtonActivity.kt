package vn.loitp.app.a.cv.bt.q

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_q_button.*
import vn.loitp.R

@LogTag("QButtonActivity")
@IsFullScreen(false)
class QButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_q_button
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = QButtonActivity::class.java.simpleName
        }
        btn.setCornerRadious(5)
        btn.setStrokeWidth(5)
        btn.setStrokeDashGap(5)
        btn.setStrokeDashWidth(90)
        btn.setBackgroundColor(LAppResource.getColor(R.color.colorPrimary))
        btn.setStrokeColor(LAppResource.getColor(R.color.colorPrimaryDark))
    }
}
