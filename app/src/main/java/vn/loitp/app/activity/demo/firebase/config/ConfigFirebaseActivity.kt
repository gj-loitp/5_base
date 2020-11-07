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

    private var firebaseRemoteConfig: FirebaseRemoteConfig? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_config_firebase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchButton.setOnClickListener {
            fetchWelcome()
        }

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig?.setConfigSettings(configSettings)
        firebaseRemoteConfig?.setDefaults(R.xml.remote_config_defaults)

        fetchWelcome()
    }

    private fun fetchWelcome() {
        welcomeTextView.text = firebaseRemoteConfig?.getString(LOADING_PHRASE_CONFIG_KEY)
        var cacheExpiration: Long = 3600 // 1 hour in seconds.
        // If your app is using developer parrallaxMode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (firebaseRemoteConfig?.info?.configSettings?.isDeveloperModeEnabled == true) {
            cacheExpiration = 0
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        firebaseRemoteConfig
                ?.fetch(cacheExpiration)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showShortInformation("Fetch Succeeded", true)

                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig?.activateFetched()
                    } else {
                        showShortInformation("Fetch Failed", true)
                    }
                    displayWelcomeMessage()
                }
    }

    private fun displayWelcomeMessage() {
        val welcomeMessage = firebaseRemoteConfig?.getString(WELCOME_MESSAGE_KEY)
        welcomeTextView.isAllCaps = firebaseRemoteConfig?.getBoolean(WELCOME_MESSAGE_CAPS_KEY) == true
        welcomeTextView.text = welcomeMessage
    }
}
