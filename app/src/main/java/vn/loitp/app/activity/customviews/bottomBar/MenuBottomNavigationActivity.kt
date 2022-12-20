package vn.loitp.app.activity.customviews.bottomBar

import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_bottom_navigation_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.bottomBar.bottombar.BottomBarActivity
import vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.ShowCaseActivity

@LogTag("BottomNavigationMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuBottomNavigationActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_bottom_navigation_bar
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuBottomNavigationActivity::class.java.simpleName
        }
        btBottomBarBlur.setSafeOnClickListener {
            startActivity(BottomBarActivity::class.java)
        }
        btExpandableBottomBar.setSafeOnClickListener {
            startActivity(ShowCaseActivity::class.java)
        }
    }

    private fun startActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        LActivityUtil.tranIn(this)
    }
}
