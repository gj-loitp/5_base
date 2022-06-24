package vn.loitp.app.activity.customviews.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_navigation_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.navigation.arcnavigationview.ArcNavigationViewActivity

@LogTag("NavigationMenuActivity")
@IsFullScreen(false)
class NavigationMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_navigation_view
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
            this.tvTitle?.text = NavigationMenuActivity::class.java.simpleName
        }
        btArcNavigation.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btArcNavigation -> intent = Intent(this, ArcNavigationViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
