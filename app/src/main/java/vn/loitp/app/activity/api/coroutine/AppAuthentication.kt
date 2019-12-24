package vn.loitp.app.activity.api.coroutine

import androidx.lifecycle.LiveData

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
object AppAuthentication : LiveData<AccessToken?>() {
    fun init() {
        value = AppPreferences.token
    }

    fun postToken(token: AccessToken?) {
        postValue(token)
        AppPreferences.token = token
    }

    fun isLoggedIn(): Boolean {

        AppPreferences.token?.let {
            return it.accessToken?.isNotEmpty() == true
        }

        return false
    }

    fun logOut() {
        postToken(null)
    }
}