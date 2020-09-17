package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_coordinator_layout.*

import vn.loitp.app.R

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/

@LayoutId(R.layout.activity_menu_coordinator_layout)
class CoordinatorLayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt0).setOnClickListener(this)
        findViewById<View>(R.id.bt1).setOnClickListener(this)
        findViewById<View>(R.id.bt2).setOnClickListener(this)
        findViewById<View>(R.id.bt3).setOnClickListener(this)
        findViewById<View>(R.id.bt4).setOnClickListener(this)
        bt5.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt0 -> intent = Intent(activity, CoordinatorLayoutWithImageViewActivity::class.java)
            R.id.bt1 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_0)
            }
            R.id.bt2 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_1)
            }
            R.id.bt3 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_2)
            }
            R.id.bt4 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_3)
            }
            R.id.bt5 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_4)
            }
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
