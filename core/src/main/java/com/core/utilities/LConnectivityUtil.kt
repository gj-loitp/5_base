package com.core.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import com.data.EventBusData

object LConnectivityUtil {

    fun initOnNetworkChange(context: Context) {
        val isConnected = isConnected(context)
        LSharedPrefsUtil.instance.putBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, isConnected)
    }

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    fun isConnected(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    fun isConnectedWifi(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @return
     */
    fun isConnectedMobile(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    fun isConnectedFast(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && LConnectivityUtil.isConnectionFast(info.type, info.subtype)
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    fun isConnectionFast(type: Int, subType: Int): Boolean {
        return if (type == ConnectivityManager.TYPE_WIFI) {
            true
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
                TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
                -> true // ~ 1-2 Mbps
                TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
                -> true // ~ 5 Mbps
                TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
                -> true // ~ 10-20 Mbps
                TelephonyManager.NETWORK_TYPE_IDEN // API level 8
                -> false // ~25 kbps
                TelephonyManager.NETWORK_TYPE_LTE // API level 11
                -> true // ~ 10+ Mbps
                // Unknown
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                else -> false
            }
        } else {
            false
        }
    }

    fun onNetworkConnectionChanged(context: Context?, isConnected: Boolean?) {
        if (context == null) {
            return
        }
        //LLog.d(TAG, "onNetworkConnectionChanged $isConnected")
        if (isConnected == true) {
            var isConnectedMobile = false
            var isConnectedWifi = false
            var isConnectedFast = false
            if (LConnectivityUtil.isConnectedMobile(context)) {
                //LLog.d(TAG, "isConnectedMobile")
                isConnectedMobile = true
            }
            if (LConnectivityUtil.isConnectedWifi(context)) {
                //LLog.d(TAG, "isConnectedWifi")
                isConnectedWifi = true
            }
            if (LConnectivityUtil.isConnectedFast(context)) {
                //LLog.d(TAG, "isConnectedFast")
                isConnectedFast = true
            }

            val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
            //LLog.d(TAG, "prevIsConnectedNetwork $prevIsConnectedNetwork")
            if (prevIsConnectedNetwork != isConnected) {
                //LLog.d(TAG, "onNetworkChange")
                LSharedPrefsUtil.instance.putBoolean(key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, data = true)
                EventBusData.instance.sendConnectChange(
                        isConnected = true,
                        isConnectedFast = isConnectedFast,
                        isConnectedWifi = isConnectedWifi,
                        isConnectedMobile = isConnectedMobile)
            }
        } else {
            //LLog.d(TAG, "!isConnected")
            val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
            //LLog.d(TAG, "prevIsConnectedNetwork $prevIsConnectedNetwork")
            if (prevIsConnectedNetwork != isConnected) {
                //LLog.d(TAG, "onNetworkChange")
                LSharedPrefsUtil.instance.putBoolean(key = LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, data = false)
                EventBusData.instance.sendConnectChange(
                        isConnected = false,
                        isConnectedFast = false,
                        isConnectedWifi = false,
                        isConnectedMobile = false)
            }
        }
    }

}
