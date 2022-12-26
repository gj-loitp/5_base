package vn.loitp.a.cv.indicator.ex

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_magic_indicator_menu_layout.*
import vn.loitp.R

@LogTag("MenuMagicIndicatorActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMagicIndicatorActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_magic_indicator_menu_layout
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
            this.tvTitle?.text = MenuMagicIndicatorActivity::class.java.simpleName
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
