package vn.loitp.app.activity.demo.firebase.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_auth_firebase.*

import vn.loitp.app.R

//https://github.com/firebase/quickstart-android

@LogTag("AuthFirebaseMenuActivity")
@IsFullScreen(false)
class AuthFirebaseMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auth_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_gg.setOnClickListener(this)
        bt_fb.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_gg -> intent = Intent(this, AuthFirebaseGoogleActivity::class.java)
            R.id.bt_fb -> intent = Intent(this, AuthFirebaseFacebookActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
