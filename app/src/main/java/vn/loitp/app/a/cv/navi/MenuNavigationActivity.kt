package vn.loitp.app.a.cv.navi

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
import kotlinx.android.synthetic.main.activity_menu_navigation_view.*
import vn.loitp.R
import vn.loitp.app.a.cv.navi.arc.ArcNavigationViewActivity

@LogTag("NavigationMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuNavigationActivity : BaseFontActivity(), View.OnClickListener {

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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuNavigationActivity::class.java.simpleName
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
