package vn.loitp.app.activity.customviews.actionbar.navigationview

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.navigationview.LNavigationView
import kotlinx.android.synthetic.main.activity_navigation_view.*
import vn.loitp.app.R
import java.util.*

class NavigationViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_navigation_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nv.apply {
            colorOn = ContextCompat.getColor(activity, R.color.red)
            colorOff = ContextCompat.getColor(activity, R.color.gray)
            tv?.setTextColor(Color.BLACK)
            LUIUtil.setTextSize(textView = this.tv, size = resources.getDimension(R.dimen.txt_medium))
        }

        val stringList = ArrayList<String>()
        for (i in 0..9) {
            stringList.add("Item $i")
        }

        nv.setStringList(stringList)
        nv.setNVCallback(nvCallback = object : LNavigationView.NVCallback {
            override fun onIndexChange(index: Int, s: String?) {
                logD("onIndexChange $index -> $s")
                tvMsg?.text = "$index -> $s"
            }
        })
        bt0.setOnClickListener { nv.setCurrenIndex(0) }
        bt1.setOnClickListener { nv.setCurrenIndex(stringList.size - 1) }
        bt2.setOnClickListener { nv.setCurrenIndex(2) }
    }
}
