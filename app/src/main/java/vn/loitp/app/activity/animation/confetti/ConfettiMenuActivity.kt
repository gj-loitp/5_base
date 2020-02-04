package vn.loitp.app.activity.animation.confetti

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import vn.loitp.app.R

//https://github.com/jinatonic/confetti
class ConfettiMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_0).setOnClickListener(this)
        findViewById<View>(R.id.bt_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_3).setOnClickListener(this)
        findViewById<View>(R.id.bt_4).setOnClickListener(this)
        findViewById<View>(R.id.bt_5).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_confetti
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_0 -> intent = Intent(activity, FallingConfettiFromTopActivity::class.java)
            R.id.bt_1 -> intent = Intent(activity, FallingConfettiFromPointActivity::class.java)
            R.id.bt_2 -> intent = Intent(activity, ExplosionActivity::class.java)
            R.id.bt_3 -> intent = Intent(activity, ShimmeringActivity::class.java)
            R.id.bt_4 -> intent = Intent(activity, FallingWithTouchActivity::class.java)
            R.id.bt_5 -> intent = Intent(activity, FallingConfettiWithListenerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
