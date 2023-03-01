package vn.loitp.up.a.cv.indicator.ex

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AMagicIndicatorMenuLayoutBinding

@LogTag("MenuMagicIndicatorActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMagicIndicatorActivity : BaseActivityFont() {

    private lateinit var binding: AMagicIndicatorMenuLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMagicIndicatorMenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuMagicIndicatorActivity::class.java.simpleName
        }
    }

    fun onClick(view: View) {
        when (view) {
            binding.btScrollableTab -> launchActivity(ScrollableTabExampleActivity::class.java)
            binding.btFixedTab -> launchActivity(FixedTabExampleActivity::class.java)
            binding.btDynamicTab -> launchActivity(DynamicTabExampleActivity::class.java)
            binding.btNoTabOnlyIndicator -> launchActivity(NoTabOnlyIndicatorExampleActivity::class.java)
            binding.btTabWithBadgeView -> launchActivity(BadgeTabExampleActivity::class.java)
            binding.btWorkWithFragmentContainer -> launchActivity(
                FragmentContainerExampleActivity::class.java
            )
            binding.btLoadCustomLayout -> launchActivity(LoadCustomLayoutExampleActivity::class.java)
            binding.btCustomNavigator -> launchActivity(CustomNavigatorExampleActivity::class.java)
        }
    }
}
