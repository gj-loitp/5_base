package vn.loitp.app.activity.demo.firebase.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import vn.loitp.app.R

//https://github.com/firebase/quickstart-android

@LayoutId(R.layout.activity_auth_firebase)
@LogTag("AuthFirebaseMenuActivity")
class AuthFirebaseMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_gg).setOnClickListener(this)
        findViewById<View>(R.id.bt_fb).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
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
