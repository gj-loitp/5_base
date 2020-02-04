package vn.loitp.app.activity.pattern

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_pattern.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.MVPActivity
import vn.loitp.app.activity.pattern.mvvm.MVVMActivity
import vn.loitp.app.activity.pattern.observerpattern.ObserverPatternActivity

class MenuPatternActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_observer_pattern).setOnClickListener(this)
        findViewById<View>(R.id.bt_mvvm).setOnClickListener(this)
        btMVP.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_pattern
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_observer_pattern -> intent = Intent(activity, ObserverPatternActivity::class.java)
            R.id.bt_mvvm -> intent = Intent(activity, MVVMActivity::class.java)
            R.id.btMVP -> intent = Intent(activity, MVPActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
