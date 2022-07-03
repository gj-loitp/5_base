package vn.loitp.app.activity.customviews.layout.splitPanelLayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.layout.splitPanel.SplitPaneLayout
import kotlinx.android.synthetic.main.activity_split_panel_layout.*
import vn.loitp.app.R
import java.text.DecimalFormat
import java.util.*

@LogTag("SplitPanelLayoutActivity")
@IsFullScreen(false)
class SplitPanelLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_split_panel_layout
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = SplitPanelLayoutActivity::class.java.simpleName
        }
        splitPaneLayout.onSplitterPositionChangedListener =
            object : SplitPaneLayout.OnSplitterPositionChangedListener {
                override fun onSplitterPositionChanged(
                    splitPaneLayout: SplitPaneLayout,
                    fromUser: Boolean
                ) {
                    val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
                    tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
                    tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
                }
            }

        splitPaneLayout.post {
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
        }
    }
}
