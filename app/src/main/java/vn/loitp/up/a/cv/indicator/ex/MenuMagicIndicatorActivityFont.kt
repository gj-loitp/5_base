package vn.loitp.up.a.cv.indicator.ex

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_magic_indicator_menu_layout.*
import vn.loitp.R

@LogTag("MenuMagicIndicatorActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMagicIndicatorActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_magic_indicator_menu_layout
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
            this.tvTitle?.text = MenuMagicIndicatorActivityFont::class.java.simpleName
        }
    }

    fun onClick(view: View) {
        when (view) {
            btScrollableTab -> startActivity(Intent(this, ScrollableTabExampleActivityFont::class.java))
            btFixedTab -> startActivity(Intent(this, FixedTabExampleActivityFont::class.java))
            btDynamicTab -> startActivity(Intent(this, DynamicTabExampleActivityFont::class.java))
            btNoTabOnlyIndicator -> startActivity(
                Intent(
                    this,
                    NoTabOnlyIndicatorExampleActivityFont::class.java
                )
            )
            btTabWithBadgeView -> startActivity(Intent(this, BadgeTabExampleActivityFont::class.java))
            btWorkWithFragmentContainer -> startActivity(
                Intent(
                    this,
                    FragmentContainerExampleActivityFont::class.java
                )
            )
            btLoadCustomLayout -> startActivity(
                Intent(
                    this,
                    LoadCustomLayoutExampleActivityFont::class.java
                )
            )
            btCustomNavigator -> startActivity(
                Intent(
                    this,
                    CustomNavigatorExampleActivityFont::class.java
                )
            )
        }
        this.tranIn()
    }
}
