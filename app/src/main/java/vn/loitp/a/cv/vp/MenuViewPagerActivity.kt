package vn.loitp.a.cv.vp

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_menu_vp.*
import vn.loitp.R
import vn.loitp.a.cv.vp.auto.ViewPagerAutoActivity
import vn.loitp.a.cv.vp.detectSwipeOut.DetectViewPagerSwipeOutActivity
import vn.loitp.a.cv.vp.detectSwipeOut2.ViewPagerSwipeOut2Activity
import vn.loitp.a.cv.vp.easyFlip.EFVPActivity
import vn.loitp.a.cv.vp.lockable.LockableViewPagerActivity
import vn.loitp.a.cv.vp.parrallax.ParallaxViewPagerActivity
import vn.loitp.a.cv.vp.refresh.RefreshViewPagerActivity
import vn.loitp.a.cv.vp.vertical.ViewPagerVerticalActivity
import vn.loitp.a.cv.vp.vp2.ViewPager2Activity
import vn.loitp.a.cv.vp.vpWithTabLayout.ViewPagerWithTabLayoutActivity

@LogTag("MenuViewPagerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuViewPagerActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_vp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuViewPagerActivity::class.java.simpleName
        }
        btAutoViewPager.setOnClickListener(this)
        btParallaxViewPager.setOnClickListener(this)
        btDetectViewPagerSwipeOut.setOnClickListener(this)
        btViewPagerTabLayout.setOnClickListener(this)
        btDetectViewPagerSwipeOut2.setOnClickListener(this)
        btLockableViewPager.setOnClickListener(this)
        btVerticalViewPager.setOnClickListener(this)
        btViewPager2.setOnClickListener(this)
        btRefreshViewPager.setOnClickListener(this)
        btEasyFlipViewPager.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutoViewPager -> intent = Intent(this, ViewPagerAutoActivity::class.java)
            btParallaxViewPager -> intent = Intent(this, ParallaxViewPagerActivity::class.java)
            btDetectViewPagerSwipeOut ->
                intent =
                    Intent(this, DetectViewPagerSwipeOutActivity::class.java)
            btViewPagerTabLayout ->
                intent =
                    Intent(this, ViewPagerWithTabLayoutActivity::class.java)
            btDetectViewPagerSwipeOut2 ->
                intent =
                    Intent(this, ViewPagerSwipeOut2Activity::class.java)
            btLockableViewPager -> intent = Intent(this, LockableViewPagerActivity::class.java)
            btVerticalViewPager -> intent = Intent(this, ViewPagerVerticalActivity::class.java)
            btViewPager2 -> intent = Intent(this, ViewPager2Activity::class.java)
            btRefreshViewPager -> intent = Intent(this, RefreshViewPagerActivity::class.java)
            btEasyFlipViewPager -> intent = Intent(this, EFVPActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
