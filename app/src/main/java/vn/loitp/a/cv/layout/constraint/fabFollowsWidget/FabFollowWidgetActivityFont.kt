package vn.loitp.a.cv.layout.constraint.fabFollowsWidget

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_fab_follow_widget.*
import vn.loitp.R

@LogTag("FabFollowWidgetActivity")
@IsFullScreen(false)
class FabFollowWidgetActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fab_follow_widget
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FabFollowWidgetActivityFont::class.java.simpleName
        }
    }
}
