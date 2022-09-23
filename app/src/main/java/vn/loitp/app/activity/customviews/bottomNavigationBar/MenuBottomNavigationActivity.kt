package vn.loitp.app.activity.customviews.bottomNavigationBar

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu_bottom_navigation_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.bottomNavigationBar.bottombar.BottomBarActivity

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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuBottomNavigationActivity::class.java.simpleName
        }
        btBottomBarBlur.setSafeOnClickListener {
            val intent = Intent(this, BottomBarActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
