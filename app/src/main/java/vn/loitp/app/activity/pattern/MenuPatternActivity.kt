package vn.loitp.app.activity.pattern

import android.content.Intent
import android.os.Bundle
import android.view.View
import loitp.basemaster.R
import vn.loitp.app.activity.pattern.observerpattern.ObserverPatternActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class MenuPatternActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_observer_pattern).setOnClickListener(this)
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
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
