package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.graphics.ColorUtils
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_xml_declared.*
import vn.loitp.app.R

@LogTag("XmlDeclaredActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class XmlDeclaredActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_xml_declared
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        color.setBackgroundColor(ColorUtils.setAlphaComponent(Color.GRAY, 60))

        expandableBottomBar.onItemSelectedListener = { v, i, _ ->
            val anim = ViewAnimationUtils.createCircularReveal(
                color,
                expandableBottomBar.x.toInt() + v.x.toInt() + v.width / 2,
                expandableBottomBar.y.toInt() + v.y.toInt() + v.height / 2, 0F,
                findViewById<View>(android.R.id.content).height.toFloat()
            )
            color.setBackgroundColor(ColorUtils.setAlphaComponent(i.activeColor, 60))
            anim.duration = 420
            anim.start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_bar, menu)
        return true
    }
}
