package vn.loitp.app.activity.customviews.actionbar.navigationviewwithtext

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.views.navigationview.LTextNavigationView
import kotlinx.android.synthetic.main.activity_navigation_view_with_text.*
import vn.loitp.app.R
import java.util.*

class NavigationViewWithTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_navigation_view_with_text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nv.apply {
            setTextNext("Next")
            setTextPrev("Prev Prev Prev")
            setTextSize(22, 18, 22)
            colorOn = ContextCompat.getColor(activity, R.color.Red)
            colorOff = ContextCompat.getColor(activity, R.color.Gray)
            tv?.setTextColor(Color.BLACK)
        }

        val stringList = ArrayList<String>()
        for (i in 0..9) {
            stringList.add("Item $i")
        }

        nv.setStringList(stringList)
        nv.setNVCallback(object : LTextNavigationView.NVCallback {
            override fun onIndexChange(index: Int, s: String?) {
                tvMsg?.text = "$index -> $s"
            }
        })
        bt0.setOnClickListener { nv.setCurrenIndex(0) }
        bt1.setOnClickListener { nv.setCurrenIndex(stringList.size - 1) }
        bt2.setOnClickListener { nv.setCurrenIndex(2) }
    }

}
