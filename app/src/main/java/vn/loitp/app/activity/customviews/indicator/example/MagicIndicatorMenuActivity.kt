package vn.loitp.app.activity.customviews.indicator.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_magic_indicator_menu_layout.*
import vn.loitp.app.R

@LogTag("MagicIndicatorMenuActivity")
@IsFullScreen(false)
class MagicIndicatorMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_magic_indicator_menu_layout
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
            this.tvTitle?.text = MagicIndicatorMenuActivity::class.java.simpleName
        }
    }

    fun onClick(view: View) {
        when (view) {
            btScrollableTab -> startActivity(Intent(this, ScrollableTabExampleActivity::class.java))
            btFixedTab -> startActivity(Intent(this, FixedTabExampleActivity::class.java))
            btDynamicTab -> startActivity(Intent(this, DynamicTabExampleActivity::class.java))
            btNoTabOnlyIndicator -> startActivity(
                Intent(
                    this,
                    NoTabOnlyIndicatorExampleActivity::class.java
                )
            )
            btTabWithBadgeView -> startActivity(Intent(this, BadgeTabExampleActivity::class.java))
            btWorkWithFragmentContainer -> startActivity(
                Intent(
                    this,
                    FragmentContainerExampleActivity::class.java
                )
            )
            btLoadCustomLayout -> startActivity(
                Intent(
                    this,
                    LoadCustomLayoutExampleActivity::class.java
                )
            )
            btCustomNavigator -> startActivity(
                Intent(
                    this,
                    CustomNavigatorExampleActivity::class.java
                )
            )
        }
        LActivityUtil.tranIn(this)
    }
}
