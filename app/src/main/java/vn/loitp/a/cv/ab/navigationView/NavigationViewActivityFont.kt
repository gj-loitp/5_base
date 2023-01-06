package vn.loitp.a.cv.ab.navigationView

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setTextSizePx
import com.loitp.views.nav.LNavigationView
import kotlinx.android.synthetic.main.a_navigation_view.*
import vn.loitp.R

@LogTag("NavigationViewActivity")
@IsFullScreen(false)
class NavigationViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_navigation_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        nv.apply {
            colorOn = getColor(R.color.red)
            colorOff = getColor(R.color.gray)
            tv?.setTextColor(Color.BLACK)
            this.tv.setTextSizePx(
                size = resources.getDimension(R.dimen.txt_medium)
            )
        }

        val stringList = ArrayList<String>()
        for (i in 0..9) {
            stringList.add("Item $i")
        }

        nv.setStringList(stringList)
        nv.setNVCallback(
            nvCallback = object : LNavigationView.NVCallback {
                @SuppressLint("SetTextI18n")
                override fun onIndexChange(index: Int, s: String?) {
                    logD("onIndexChange $index -> $s")
                    tvMsg?.text = "$index -> $s"
                }
            }
        )
        bt0.setOnClickListener { nv.setCurrentIndex(0) }
        bt1.setOnClickListener { nv.setCurrentIndex(stringList.size - 1) }
        bt2.setOnClickListener { nv.setCurrentIndex(2) }
    }
}
