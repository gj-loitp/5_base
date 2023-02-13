package vn.loitp.up.a.cv.menu

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.a.cv.menu.drawerBehavior.DrawerBehaviorMainActivityFont
import vn.loitp.databinding.AMenuMenuBinding
import vn.loitp.up.a.cv.menu.reside.ResideMenuActivity

@LogTag("MenuMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMenuActivity : BaseActivityFont() {
    private lateinit var binding: AMenuMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuMenuActivity::class.java.simpleName
        }
        binding.btResideMenu.setSafeOnClickListener {
            launchActivity(ResideMenuActivity::class.java)
        }
        binding.btDrawerBehavior.setSafeOnClickListener {
            launchActivity(DrawerBehaviorMainActivityFont::class.java)
        }
    }
}
