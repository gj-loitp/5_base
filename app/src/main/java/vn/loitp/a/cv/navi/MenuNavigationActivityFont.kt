package vn.loitp.a.cv.navi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_navi_menu.*
import vn.loitp.R
import vn.loitp.a.cv.navi.arc.ArcNavigationViewActivityFont

@LogTag("NavigationMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuNavigationActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_navi_menu
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
            this.tvTitle?.text = MenuNavigationActivityFont::class.java.simpleName
        }
        btArcNavigation.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btArcNavigation -> intent = Intent(this, ArcNavigationViewActivityFont::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
