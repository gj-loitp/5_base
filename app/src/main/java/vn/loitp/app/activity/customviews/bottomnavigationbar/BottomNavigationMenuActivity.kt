package vn.loitp.app.activity.customviews.bottomnavigationbar

import android.content.Intent
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_bottom_navigation_bar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar.BottomBarActivity

@LayoutId(R.layout.activity_bottom_navigation_bar_menu)
@LogTag("BottomNavigationMenuActivity")
class BottomNavigationMenuActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btBottomBarBlur.setOnClickListener {
            val intent = Intent(activity, BottomBarActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
