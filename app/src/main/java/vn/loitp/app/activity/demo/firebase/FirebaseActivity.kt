package vn.loitp.app.activity.demo.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LFCMUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_firebase.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R

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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FirebaseActivity::class.java.simpleName
        }

        btTestCrashFirebase.isVisible = BuildConfig.DEBUG
        btTestCrashFirebase.setSafeOnClickListener {
            LFCMUtil.testCrash()
        }

        btRemoteConfig.setSafeOnClickListener {
            testRemoteConfig()
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
}
