package com.loitp.core.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.loitpcore.core.utilities.LSharedPrefsUtil
import com.loitpcore.data.EventBusData

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LConnectivityUtil {

    companion object {
        fun initOnNetworkChange() {
            val isConnected = isConnected()
            LSharedPrefsUtil.instance.putBoolean(
                LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK,
                isConnected
            )
        }

        @Suppress("DEPRECATION")
        fun isConnected(): Boolean {
            val connectivityManager =
                LAppResource.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?: return false
            val result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

            return result
        }

        fun onNetworkConnectionChanged(isConnected: Boolean?) {
            if (isConnected == true) {
                val prevIsConnectedNetwork =
                    LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
                if (prevIsConnectedNetwork != isConnected) {
                    LSharedPrefsUtil.instance.putBoolean(
                        key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK,
                        data = true
                    )
                    EventBusData.instance.sendConnectChange(isConnected = true)
                }
            } else {
                val prevIsConnectedNetwork =
                    LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
                if (prevIsConnectedNetwork != isConnected) {
                    LSharedPrefsUtil.instance.putBoolean(
                        key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK,
                        data = false
                    )
                    EventBusData.instance.sendConnectChange(isConnected = false)
                }
            }
        }
    }
}
