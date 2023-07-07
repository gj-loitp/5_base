package vn.loitp.up.a.cv.vp

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuVpBinding
import vn.loitp.up.a.cv.vp.auto.ViewPagerAutoActivity
import vn.loitp.up.a.cv.vp.detectSwipeOut.DetectViewPagerSwipeOutActivity
import vn.loitp.up.a.cv.vp.detectSwipeOut2.ViewPagerSwipeOut2Activity
import vn.loitp.up.a.cv.vp.easyFlip.EFVPActivity
import vn.loitp.up.a.cv.vp.lockable.LockableViewPagerActivity
import vn.loitp.up.a.cv.vp.parrallax.ParallaxViewPagerActivity
import vn.loitp.up.a.cv.vp.refresh.RefreshViewPagerActivity
import vn.loitp.up.a.cv.vp.vertical.ViewPagerVerticalActivity
import vn.loitp.up.a.cv.vp.vp2.ViewPager2Activity
import vn.loitp.up.a.cv.vp.vpWithTabLayout.ViewPagerWithTabLayoutActivity

@LogTag("MenuViewPagerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuViewPagerActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuVpBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuVpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuViewPagerActivity::class.java.simpleName
        }
        binding.btAutoViewPager.setOnClickListener(this)
        binding.btParallaxViewPager.setOnClickListener(this)
        binding.btDetectViewPagerSwipeOut.setOnClickListener(this)
        binding.btViewPagerTabLayout.setOnClickListener(this)
        binding.btDetectViewPagerSwipeOut2.setOnClickListener(this)
        binding.btLockableViewPager.setOnClickListener(this)
        binding.btVerticalViewPager.setOnClickListener(this)
        binding.btViewPager2.setOnClickListener(this)
        binding.btRefreshViewPager.setOnClickListener(this)
        binding.btEasyFlipViewPager.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btAutoViewPager -> launchActivity(ViewPagerAutoActivity::class.java)
            binding.btParallaxViewPager -> launchActivity(ParallaxViewPagerActivity::class.java)
            binding.btDetectViewPagerSwipeOut -> launchActivity(DetectViewPagerSwipeOutActivity::class.java)
            binding.btViewPagerTabLayout -> launchActivity(ViewPagerWithTabLayoutActivity::class.java)
            binding.btDetectViewPagerSwipeOut2 -> launchActivity(ViewPagerSwipeOut2Activity::class.java)
            binding.btLockableViewPager -> launchActivity(LockableViewPagerActivity::class.java)
            binding.btVerticalViewPager -> launchActivity(ViewPagerVerticalActivity::class.java)
            binding.btViewPager2 -> launchActivity(ViewPager2Activity::class.java)
            binding.btRefreshViewPager -> launchActivity(RefreshViewPagerActivity::class.java)
            binding.btEasyFlipViewPager -> launchActivity(EFVPActivity::class.java)
        }
    }
}
