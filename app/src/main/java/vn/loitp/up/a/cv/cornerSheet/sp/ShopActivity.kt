package vn.loitp.up.a.cv.cornerSheet.sp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import vn.loitp.R
import vn.loitp.up.a.cv.cornerSheet.sp.shop.ShopFragment

@LogTag("ShopActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShopActivity : BaseActivityFont() {

    lateinit var supportFragment: SupportFragment

    override fun setLayoutResourceId(): Int {
        return R.layout.a_support
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.let {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // for drawing behind status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

            //make system bar to be translucent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            //make status bar color transparent
            window.statusBarColor = Color.TRANSPARENT

            var flags = it.decorView.systemUiVisibility
            // make dark status bar icons
            flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            window.decorView.systemUiVisibility = flags
        }

        supportFragment =
            supportFragmentManager.findFragmentById(R.id.support_fragment) as SupportFragment

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    ShopFragment(), "shop"
                )
                .commit()
        }
    }
}
