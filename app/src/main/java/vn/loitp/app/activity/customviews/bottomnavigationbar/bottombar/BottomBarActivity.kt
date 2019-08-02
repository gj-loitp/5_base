package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.views.LToast
import com.views.bottombar.LBottomBar
import kotlinx.android.synthetic.main.activity_bottom_bar.*
import loitp.basemaster.R

class BottomBarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = findViewById<TextView>(R.id.tv)
        tv.text = LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp)
        val lBottomBar = findViewById<LBottomBar>(R.id.bottom_bar)
        with(lBottomBar) {
            paddingOnInDp = 15
            paddingOffInDp = 15
            colorIvOn = R.color.Red
            colorIvOff = R.color.Pink
            setItem0(R.drawable.baseline_bug_report_black_48, "Bug report")
            setItem1(R.drawable.baseline_add_black_48, "Add")
            setItem2(R.drawable.baseline_chat_black_48dp, "Chat")
            setItem3(R.drawable.baseline_clear_black_48, "Clear")
            setItem4(R.drawable.baseline_cloud_download_black_48, "Cloud")
            setItem5(R.drawable.baseline_picture_in_picture_alt_white_48dp, "Picture")
            setTechniques(Techniques.Pulse)
            setOnItemClick(object : LBottomBar.Callback {
                override fun onClickItem(position: Int) {
                    LToast.show(activity, "Touch $position")
                }
            })
        }
        findViewById<View>(R.id.bt_blur_view_red).setOnClickListener { lBottomBar.blurView.setOverlayColor(ContextCompat.getColor(activity, R.color.RedTrans)) }
        findViewById<View>(R.id.bt_blur_view_green).setOnClickListener { lBottomBar.blurView.setOverlayColor(ContextCompat.getColor(activity, R.color.GreenTrans)) }
        findViewById<View>(R.id.bt_count_1).setOnClickListener { lBottomBar.setCount(1) }
        findViewById<View>(R.id.bt_count_3).setOnClickListener { lBottomBar.setCount(3) }
        findViewById<View>(R.id.bt_count_5).setOnClickListener { lBottomBar.setCount(5) }
        findViewById<View>(R.id.bt_count_6).setOnClickListener { lBottomBar.setCount(6) }
        findViewById<View>(R.id.bt_cl_0).setOnClickListener {
            lBottomBar.setColorTextView(R.color.Red)
        }
        findViewById<View>(R.id.bt_cl_1).setOnClickListener { lBottomBar.setColorTextView(R.color.Black) }
        findViewById<View>(R.id.bt_cl_2).setOnClickListener { lBottomBar.setColorTextView(R.color.Blue) }
        btShowText.setOnClickListener { lBottomBar.isAlwayShowText = true }
        btHideText.setOnClickListener { lBottomBar.isAlwayShowText = false }
        LUIUtil.setDelay(5000, Runnable {
            lBottomBar?.setPerformItemClick(4)
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_bottom_bar
    }
}
