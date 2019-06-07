package vn.loitp.app.activity.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.tutorial.retrofit2.Retrofit2Activity
import vn.loitp.app.activity.tutorial.rxjava2.MenuRxJava2Activity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class MenuTutorialActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_rx_java_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_retrofit_2).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_tutorial_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_rx_java_2 -> intent = Intent(activity, MenuRxJava2Activity::class.java)
            R.id.bt_retrofit_2 -> intent = Intent(activity, Retrofit2Activity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
