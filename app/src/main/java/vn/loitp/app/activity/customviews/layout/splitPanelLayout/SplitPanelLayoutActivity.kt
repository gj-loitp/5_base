package vn.loitp.app.activity.customviews.layout.splitPanelLayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.layout.splitPanel.SplitPaneLayout
import kotlinx.android.synthetic.main.activity_split_panel_layout.*
import vn.loitp.R
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
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
