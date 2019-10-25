package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
class CoordinatorLayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_0).setOnClickListener(this)
        findViewById<View>(R.id.bt_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_3).setOnClickListener(this)
        findViewById<View>(R.id.bt_4).setOnClickListener(this)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_coordinator_layout
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_0 -> intent = Intent(activity, CoordinatorLayoutWithImageViewActivity::class.java)
            R.id.bt_1 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_0)
            }
            R.id.bt_2 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_1)
            }
            R.id.bt_3 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_2)
            }
            R.id.bt_4 -> {
                intent = Intent(activity, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_3)
            }
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
