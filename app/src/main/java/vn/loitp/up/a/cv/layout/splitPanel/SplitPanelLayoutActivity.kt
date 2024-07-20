package vn.loitp.up.a.cv.layout.splitPanel

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.splitPanel.SplitPaneLayout
import vn.loitp.databinding.ALayoutSplitPanelBinding
import java.text.DecimalFormat
import java.util.*

@LogTag("SplitPanelLayoutActivity")
@IsFullScreen(false)
class SplitPanelLayoutActivity : BaseActivityFont() {

    private lateinit var binding: ALayoutSplitPanelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutSplitPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = SplitPanelLayoutActivity::class.java.simpleName
        }
        binding.splitPaneLayout.onSplitterPositionChangedListener =
            object : SplitPaneLayout.OnSplitterPositionChangedListener {
                override fun onSplitterPositionChanged(
                    splitPaneLayout: SplitPaneLayout, fromUser: Boolean
                ) {
                    val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
                    binding.tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
                    binding.tvSecond.text =
                        percent.format(1f - splitPaneLayout.splitterPositionPercent)
                }
            }

        binding.splitPaneLayout.post {
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            binding.tvFirst.text = percent.format(binding.splitPaneLayout.splitterPositionPercent)
            binding.tvSecond.text =
                percent.format(1f - binding.splitPaneLayout.splitterPositionPercent)
        }
    }
}
