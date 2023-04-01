package vn.loitp.up.a.cv.ab

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.cv.ab.collapsingToolbarLayout.CollapsingToolbarLayoutActivityFont
import vn.loitp.a.cv.ab.collapsingToolbarLayoutWithTabLayout.CollapsingToolbarWithTabLayoutActivityFont
import vn.loitp.up.a.cv.ab.l.LActionbarActivity
import vn.loitp.up.a.cv.ab.navigationView.NavigationViewActivity
import vn.loitp.up.a.cv.ab.navigationViewWithText.NavigationViewWithTextActivity
import vn.loitp.databinding.AMenuActionBarBinding

@LogTag("MenuActionBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuActionBarActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AMenuActionBarBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuActionBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActionBarActivity::class.java.simpleName
        }
        binding.btCollapsingToolBarLayout.setOnClickListener(this)
        binding.btLActionBar.setOnClickListener(this)
        binding.btCollapsingToolbarWithTabLayout.setOnClickListener(this)
        binding.btNavigationView.setOnClickListener(this)
        binding.btNavigationViewWithText.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btCollapsingToolBarLayout -> launchActivity(CollapsingToolbarLayoutActivityFont::class.java)
            binding.btCollapsingToolbarWithTabLayout -> launchActivity(
                CollapsingToolbarWithTabLayoutActivityFont::class.java
            )
            binding.btLActionBar -> launchActivity(LActionbarActivity::class.java)
            binding.btNavigationView -> launchActivity(NavigationViewActivity::class.java)
            binding.btNavigationViewWithText -> launchActivity(NavigationViewWithTextActivity::class.java)

        }

    }
}
