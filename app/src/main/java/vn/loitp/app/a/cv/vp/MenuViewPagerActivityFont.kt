package vn.loitp.app.a.cv.vp

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_view_pager.*
import vn.loitp.R
import vn.loitp.app.a.cv.vp.auto.ViewPagerAutoActivityFont
import vn.loitp.app.a.cv.vp.detectSwipeOut.DetectViewPagerSwipeOutActivityFont
import vn.loitp.app.a.cv.vp.detectSwipeOut2.ViewPagerSwipeOut2ActivityFont
import vn.loitp.app.a.cv.vp.easyFlip.EFVPActivityFont
import vn.loitp.app.a.cv.vp.lockable.LockableViewPagerActivityFont
import vn.loitp.app.a.cv.vp.parrallax.ParallaxViewPagerActivityFont
import vn.loitp.app.a.cv.vp.refresh.RefreshViewPagerActivityFont
import vn.loitp.app.a.cv.vp.vertical.ViewPagerVerticalActivityFont
import vn.loitp.app.a.cv.vp.vp2.ViewPager2ActivityFont
import vn.loitp.app.a.cv.vp.vpWithTabLayout.ViewPagerWithTabLayoutActivityFont

@LogTag("MenuViewPagerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuViewPagerActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_view_pager
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
            this.tvTitle?.text = MenuViewPagerActivityFont::class.java.simpleName
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
            btAutoViewPager -> intent = Intent(this, ViewPagerAutoActivityFont::class.java)
            btParallaxViewPager -> intent = Intent(this, ParallaxViewPagerActivityFont::class.java)
            btDetectViewPagerSwipeOut ->
                intent =
                    Intent(this, DetectViewPagerSwipeOutActivityFont::class.java)
            btViewPagerTabLayout ->
                intent =
                    Intent(this, ViewPagerWithTabLayoutActivityFont::class.java)
            btDetectViewPagerSwipeOut2 ->
                intent =
                    Intent(this, ViewPagerSwipeOut2ActivityFont::class.java)
            btLockableViewPager -> intent = Intent(this, LockableViewPagerActivityFont::class.java)
            btVerticalViewPager -> intent = Intent(this, ViewPagerVerticalActivityFont::class.java)
            btViewPager2 -> intent = Intent(this, ViewPager2ActivityFont::class.java)
            btRefreshViewPager -> intent = Intent(this, RefreshViewPagerActivityFont::class.java)
            btEasyFlipViewPager -> intent = Intent(this, EFVPActivityFont::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
