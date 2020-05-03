package vn.loitp.app.activity.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_tutorial_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.retrofit2.Retrofit2Activity
import vn.loitp.app.activity.tutorial.rxjava2.MenuRxJava2Activity

class MenuTutorialActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btRxJava2.setOnClickListener(this)
        btRetrofit2.setOnClickListener(this)
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
        when (v) {
            btRxJava2 -> intent = Intent(activity, MenuRxJava2Activity::class.java)
            btRetrofit2 -> intent = Intent(activity, Retrofit2Activity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
