package vn.loitp.app.a.cv.layout.constraint.fabFollowsWidget

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fab_follow_widget.*
import vn.loitp.R

@LogTag("FabFollowWidgetActivity")
@IsFullScreen(false)
class FabFollowWidgetActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fab_follow_widget
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
            this.tvTitle?.text = FabFollowWidgetActivity::class.java.simpleName
        }
    }
}