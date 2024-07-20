package vn.loitp.up.a.cv.bb.expandable.screens

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.graphics.ColorUtils
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.AXmlDeclaredBinding

@LogTag("XmlDeclaredActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class XmlDeclaredActivity : BaseActivityFont() {
    private lateinit var binding: AXmlDeclaredBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AXmlDeclaredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.color.setBackgroundColor(ColorUtils.setAlphaComponent(Color.GRAY, 60))

        binding.expandableBottomBar.onItemSelectedListener = { v, i, _ ->
            val anim = ViewAnimationUtils.createCircularReveal(
                binding.color,
                binding.expandableBottomBar.x.toInt() + v.x.toInt() + v.width / 2,
                binding.expandableBottomBar.y.toInt() + v.y.toInt() + v.height / 2, 0F,
                findViewById<View>(android.R.id.content).height.toFloat()
            )
            binding.color.setBackgroundColor(ColorUtils.setAlphaComponent(i.activeColor, 60))
            anim.duration = 420
            anim.start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_bar, menu)
        return true
    }
}
