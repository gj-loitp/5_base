package vn.loitp.app.a.demo.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LFCMUtil
import com.loitp.core.utilities.LUIUtil
import com.onesignal.OneSignal
import com.onesignal.OneSignal.PostNotificationResponseHandler
import kotlinx.android.synthetic.main.activity_firebase.*
import org.json.JSONObject
import vn.loitp.BuildConfig
import vn.loitp.R

@LogTag("FirebaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class FirebaseActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FirebaseActivity::class.java.simpleName
        }

        btTestCrashFirebase.isVisible = BuildConfig.DEBUG
        btTestCrashFirebase.setSafeOnClickListener {
            LFCMUtil.testCrash()
        }

        btRemoteConfig.setSafeOnClickListener {
            testRemoteConfig()
        }

        btPushOneSignal.setSafeOnClickListener {
            pushNotiByOneSignal()
        }
    }

    private fun testRemoteConfig() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    showShortInformation("Fetch and activate succeeded updated $updated")
                    btRemoteConfig.text = remoteConfig["key_test"].asString()
                } else {
                    showShortError("Fetch failed")
                }
            }
    }

    private fun pushNotiByOneSignal() {
        if (!BuildConfig.DEBUG) {
            showShortError("This feature only available for @Roy93Group")
            return
        }
        val msg = "${getString(R.string.app_name)} miss you!!!"
        try {
            val userId = OneSignal.getDeviceState()?.userId
            if (userId.isNullOrEmpty()) {
                logE("userId isNullOrEmpty -> return")
                return
            }
            OneSignal.postNotification(
                JSONObject("{'contents': {'en': '$msg'}, 'include_player_ids': ['$userId']}"),
                object : PostNotificationResponseHandler {
                    override fun onSuccess(response: JSONObject) {
                        logD("postNotification Success: $response")
                    }

                    override fun onFailure(response: JSONObject) {
                        logD("postNotification Failure: $response")
                    }
                })
        } catch (e: Exception) {
            logE("$e")
        }
    }
}
