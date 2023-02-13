package vn.loitp.up.a.demo.firebase

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
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.testCrash
import com.onesignal.OneSignal
import com.onesignal.OneSignal.PostNotificationResponseHandler
import org.json.JSONObject
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.databinding.AFirebaseBinding

@LogTag("FirebaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class FirebaseActivity : BaseActivityFont() {
    private lateinit var binding: AFirebaseBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FirebaseActivity::class.java.simpleName
        }

        binding.btTestCrashFirebase.isVisible = BuildConfig.DEBUG
        binding.btTestCrashFirebase.setSafeOnClickListener {
            testCrash()
        }

        binding.btRemoteConfig.setSafeOnClickListener {
            testRemoteConfig()
        }

        binding.btPushOneSignal.setSafeOnClickListener {
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
                    binding.btRemoteConfig.text = remoteConfig["key_test"].asString()
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
