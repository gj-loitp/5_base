package com.loitp.core.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.loitp.data.EventBusData

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//fun Context.initOnNetworkChange() {
//    val isConnected = isConnected()
//    putBooleanPref(
//        KEY_BOOLEAN_IS_CONNECTED_NETWORK,
//        isConnected
//    )
//}

fun Context.isConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkCapabilities = connectivityManager?.activeNetwork ?: return false
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

//fun Context.onNetworkConnectionChanged(isConnected: Boolean?) {
//    if (isConnected == true) {
//        val prevIsConnectedNetwork =
//            getBooleanPref(KEY_BOOLEAN_IS_CONNECTED_NETWORK)
//        if (prevIsConnectedNetwork != isConnected) {
//            putBooleanPref(
//                key = KEY_BOOLEAN_IS_CONNECTED_NETWORK,
//                data = true
//            )
//            EventBusData.instance.sendConnectChange(isConnected = true)
//        }
//    } else {
//        val prevIsConnectedNetwork =
//            getBooleanPref(KEY_BOOLEAN_IS_CONNECTED_NETWORK)
//        if (prevIsConnectedNetwork != isConnected) {
//            putBooleanPref(
//                key = KEY_BOOLEAN_IS_CONNECTED_NETWORK,
//                data = false
//            )
//            EventBusData.instance.sendConnectChange(isConnected = false)
//        }
//    }
//}
