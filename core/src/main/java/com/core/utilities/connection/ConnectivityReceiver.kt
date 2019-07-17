package com.core.utilities.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class ConnectivityReceiver internal constructor(private val mConnectivityReceiverListener: ConnectivityReceiverListener)
    : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected(context))
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo == null) {
                return false
            }
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}