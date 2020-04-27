package vn.loitp.app.activity.customviews.layout.splitpanellayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.layout.splitpanellayout.SplitPaneLayout
import kotlinx.android.synthetic.main.activity_split_panel_layout.*
import vn.loitp.app.R
import java.text.DecimalFormat
import java.util.*

class SplitPanelLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout.onSplitterPositionChangedListener = SplitPaneLayout.OnSplitterPositionChangedListener { _, _ ->
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(layout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - layout.splitterPositionPercent)
        }
        layout.post(Runnable {
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(layout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - layout.splitterPositionPercent)
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_split_panel_layout
    }
}
