package vn.loitp.a.cv.bt.q

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_q_button.*
import vn.loitp.R

@LogTag("QButtonActivity")
@IsFullScreen(false)
class QButtonActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_q_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = QButtonActivityFont::class.java.simpleName
        }
        btn.setCornerRadious(5)
        btn.setStrokeWidth(5)
        btn.setStrokeDashGap(5)
        btn.setStrokeDashWidth(90)
        btn.setBackgroundColor(getColor(R.color.colorPrimary))
        btn.setStrokeColor(getColor(R.color.colorPrimaryDark))
    }
}
