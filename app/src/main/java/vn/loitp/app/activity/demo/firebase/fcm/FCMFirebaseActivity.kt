package vn.loitp.app.activity.demo.firebase.fcm

import android.os.Bundle
import com.BuildConfig
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LFCMUtil
import com.interfaces.Callback1
import kotlinx.android.synthetic.main.activity_fcm_firebase.*
import vn.loitp.app.R

//https://docs.google.com/document/d/1LIkZgTWQB7FWVHUrc-ZrQaMVJkm6lbuMwirr1XCPwcA/edit
@LogTag("FCMFirebaseActivity")
@IsFullScreen(false)
class FCMFirebaseActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fcm_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fcmKey = "AIzaSyDmo9cOZx7wb1R1cL7zPhw1YRxEpJFOzgo"
        btSendFcm.setOnClickListener {
            if (BuildConfig.DEBUG) {
                LFCMUtil.sendNotification(
                        key = fcmKey,
                        body = "Hello! This is a notification! " + System.currentTimeMillis(),
                        onComplete = null,
                        onError = null)
            } else {
                LDialogUtil.showDialog1(
                        context = this@FCMFirebaseActivity,
                        title = "Message",
                        msg = "This feature is only available in debug mode",
                        button1 = getString(R.string.confirm),
                        callback1 = object : Callback1 {
                            override fun onClick1() {
                                //do nothing
                            }
                        })
            }
        }
    }
}
