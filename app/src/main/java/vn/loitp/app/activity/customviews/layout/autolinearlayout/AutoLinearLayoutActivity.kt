package vn.loitp.app.activity.customviews.layout.autolinearlayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import vn.loitp.app.R
import vn.loitp.app.activity.EmptyActivity

@LogTag("AutoLinearLayoutActivity")
@IsFullScreen(false)
class AutoLinearLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_auto_linear
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/AlbertGrobas/AutoLinearLayout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
    }
}
