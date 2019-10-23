package vn.loitp.app.activity.demo.firebase.auth

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R

//https://github.com/firebase/quickstart-android
class AuthFirebaseMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_gg).setOnClickListener(this)
        findViewById<View>(R.id.bt_fb).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auth_firebase
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_gg -> intent = Intent(activity, AuthFirebaseGoogleActivity::class.java)
            R.id.bt_fb -> intent = Intent(activity, AuthFirebaseFacebookActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
