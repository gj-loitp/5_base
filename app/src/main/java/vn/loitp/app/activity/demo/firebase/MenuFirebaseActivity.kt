package vn.loitp.app.activity.demo.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_menu_firebase.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.firebase.admob.FirebaseAdmobActivity
import vn.loitp.app.activity.demo.firebase.auth.AuthFirebaseMenuActivity
import vn.loitp.app.activity.demo.firebase.config.ConfigFirebaseActivity
import vn.loitp.app.activity.demo.firebase.database.DatabaseFirebaseSignInActivity
import vn.loitp.app.activity.demo.firebase.databasesimple.DatabaseSimpleFirebaseActivity
import vn.loitp.app.activity.demo.firebase.fcm.FCMFirebaseActivity
import vn.loitp.app.activity.demo.firebase.invite.InviteFirebaseActivity
import java.io.IOException

//https://github.com/firebase/quickstart-android

@LayoutId(R.layout.activity_menu_firebase)
@LogTag("MenuFirebaseActivity")
@IsFullScreen(false)
class MenuFirebaseActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_admob.setOnClickListener(this)
        bt_auth.setOnClickListener(this)
        bt_config.setOnClickListener(this)
        btDatabase.setOnClickListener(this)
        bt_invite.setOnClickListener(this)
        bt_database_simple.setOnClickListener(this)
        bt_fcm.setOnClickListener(this)
        btGetFCMToken.setOnClickListener(this)
        btResetInstanceId.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt_admob -> intent = Intent(this, FirebaseAdmobActivity::class.java)
            bt_auth -> intent = Intent(this, AuthFirebaseMenuActivity::class.java)
            bt_config -> intent = Intent(this, ConfigFirebaseActivity::class.java)
            btDatabase -> intent = Intent(this, DatabaseFirebaseSignInActivity::class.java)
            bt_invite -> intent = Intent(this, InviteFirebaseActivity::class.java)
            bt_database_simple -> intent = Intent(this, DatabaseSimpleFirebaseActivity::class.java)
            bt_fcm -> intent = Intent(this, FCMFirebaseActivity::class.java)
            btGetFCMToken -> {
                getFCMToken()
            }
            btResetInstanceId -> {
                resetInstanceId()
            }
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val fcmToken = task.result?.token
                    logD("FCM token: $fcmToken")
                    showDialogMsg("getFCMToken fcmToken: $fcmToken")
                })
    }

    private fun resetInstanceId() {
        Thread(Runnable {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId()
                FirebaseInstanceId.getInstance().instanceId
                logD("InstanceId removed and regenerated.")
                runOnUiThread {
                    showDialogMsg("resetInstanceId success")
                }
            } catch (e: IOException) {
                logE("resetInstanceId $e")
                showDialogError("resetInstanceId failed: $e")
            }
        }).start()
    }
}
