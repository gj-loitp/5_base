package vn.loitp.app.activity.customviews.bottomnavigationbar

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_bottom_navigation_bar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar.BottomBarActivity

@LogTag("BottomNavigationMenuActivity")
@IsFullScreen(false)
class BottomNavigationMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_bottom_navigation_bar_menu
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = BottomNavigationMenuActivity::class.java.simpleName
        }
        btBottomBarBlur.setSafeOnClickListener {
            val intent = Intent(this, BottomBarActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
