package vn.loitp.app.activity.demo.firebase.config

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_config_firebase.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R

//https://github.com/firebase/quickstart-android
@LogTag("ConfigFirebaseActivity")
@IsFullScreen(false)
class ConfigFirebaseActivity : BaseFontActivity() {

    companion object {
        private const val LOADING_PHRASE_CONFIG_KEY = "loading_phrase"
        private const val WELCOME_MESSAGE_KEY = "welcome_message"
        private const val WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps"
    }

    private var firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_config_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchButton.setOnClickListener {
            fetchWelcome()
        }

        firebaseRemoteConfig.apply {
            val timeInS = if (BuildConfig.DEBUG) {
                0L
            } else {
                3600L
            }
            this.setConfigSettingsAsync(
                    FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(timeInS)
                            .build()
            )
            this.setDefaultsAsync(R.xml.remote_config_defaults)
        }

        fetchWelcome()
    }

    private fun fetchWelcome() {
        welcomeTextView.text = firebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY)

//        var cacheExpiration: Long = 3600 // 1 hour in seconds.
//        if (firebaseRemoteConfig?.info?.configSettings?.isDeveloperModeEnabled == true) {
//            cacheExpiration = 0
//        }
//        firebaseRemoteConfig
//                ?.fetch(cacheExpiration)
//                ?.addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        showShortInformation("Fetch Succeeded", true)
//
//                        // After config data is successfully fetched, it must be activated before newly fetched
//                        // values are returned.
//                        firebaseRemoteConfig?.activateFetched()
//                    } else {
//                        showShortInformation("Fetch Failed", true)
//                    }
//                    displayWelcomeMessage()
//                }

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(
                this
        ) { task ->
            if (task.isSuccessful) {
                val updated = task.result
                showShortInformation("Fetch Succeeded updated $updated", true)
                firebaseRemoteConfig.activate()
            } else {
                showShortInformation("Fetch Failed", true)
            }
            displayWelcomeMessage()
        }
    }

    private fun displayWelcomeMessage() {
        val welcomeMessage = firebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY)
        welcomeTextView.isAllCaps = firebaseRemoteConfig.getBoolean(WELCOME_MESSAGE_CAPS_KEY) == true
        welcomeTextView.text = welcomeMessage
    }
}
