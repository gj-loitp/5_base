package vn.loitp.app.activity.demo.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
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
class MenuFirebaseActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_admob).setOnClickListener(this)
        findViewById<View>(R.id.bt_auth).setOnClickListener(this)
        findViewById<View>(R.id.bt_config).setOnClickListener(this)
        findViewById<View>(R.id.btDatabase).setOnClickListener(this)
        findViewById<View>(R.id.bt_invite).setOnClickListener(this)
        findViewById<View>(R.id.bt_database_simple).setOnClickListener(this)
        findViewById<View>(R.id.bt_fcm).setOnClickListener(this)
        btGetFCMToken.setOnClickListener(this)
        btResetInstanceId.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_firebase
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_admob -> intent = Intent(activity, FirebaseAdmobActivity::class.java)
            R.id.bt_auth -> intent = Intent(activity, AuthFirebaseMenuActivity::class.java)
            R.id.bt_config -> intent = Intent(activity, ConfigFirebaseActivity::class.java)
            R.id.btDatabase -> intent = Intent(activity, DatabaseFirebaseSignInActivity::class.java)
            R.id.bt_invite -> intent = Intent(activity, InviteFirebaseActivity::class.java)
            R.id.bt_database_simple -> intent = Intent(activity, DatabaseSimpleFirebaseActivity::class.java)
            R.id.bt_fcm -> intent = Intent(activity, FCMFirebaseActivity::class.java)
            R.id.btGetFCMToken -> {
                getFCMToken()
            }
            R.id.btResetInstanceId -> {
                resetInstanceId()
            }
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
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
