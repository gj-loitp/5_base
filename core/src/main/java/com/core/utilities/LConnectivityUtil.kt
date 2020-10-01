package com.core.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.data.EventBusData

class LConnectivityUtil {

    companion object {
        fun initOnNetworkChange() {
            val isConnected = isConnected()
            LSharedPrefsUtil.instance.putBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, isConnected)
        }

        @Suppress("DEPRECATION")
        fun isConnected(): Boolean {
            var result = false
            val connectivityManager = LAppResource.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities)
                        ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }

            return result
        }

        fun onNetworkConnectionChanged(isConnected: Boolean?) {
            if (isConnected == true) {
                val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
                if (prevIsConnectedNetwork != isConnected) {
                    LSharedPrefsUtil.instance.putBoolean(key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, data = true)
                    EventBusData.instance.sendConnectChange(isConnected = true)
                }
            } else {
                val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
                if (prevIsConnectedNetwork != isConnected) {
                    LSharedPrefsUtil.instance.putBoolean(key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, data = false)
                    EventBusData.instance.sendConnectChange(isConnected = false)
                }
            }
        }
    }

}
