package com.core.utilities.connection

import android.annotation.TargetApi
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import com.core.utilities.LConnectivityUtil
import com.core.utilities.LSharedPrefsUtil
import com.data.EventBusData

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LConectifyService : JobService(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val TAG = LConectifyService::class.java.simpleName

    private var mConnectivityReceiver: ConnectivityReceiver? = null

    override fun onCreate() {
        super.onCreate()
        //LLog.d(TAG, "Service created")
        mConnectivityReceiver = ConnectivityReceiver(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //LLog.d(TAG, "onStartCommand")
        return Service.START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
        //LLog.d(TAG, "onStartJob" + mConnectivityReceiver)
        try {
            mConnectivityReceiver?.let { cr ->
                registerReceiver(cr, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        //LLog.d(TAG, "onStopJob")
        try {
            mConnectivityReceiver?.let { cr ->
                unregisterReceiver(cr)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        //LLog.d(TAG, "onNetworkConnectionChanged $isConnected")
        if (isConnected) {
            var isConnectedMobile = false
            var isConnectedWifi = false
            var isConnectedFast = false
            if (LConnectivityUtil.isConnectedMobile(this)) {
                //LLog.d(TAG, "isConnectedMobile")
                isConnectedMobile = true
            }
            if (LConnectivityUtil.isConnectedWifi(this)) {
                //LLog.d(TAG, "isConnectedWifi")
                isConnectedWifi = true
            }
            if (LConnectivityUtil.isConnectedFast(this)) {
                //LLog.d(TAG, "isConnectedFast")
                isConnectedFast = true
            }

            val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
            //LLog.d(TAG, "prevIsConnectedNetwork $prevIsConnectedNetwork")
            if (prevIsConnectedNetwork != isConnected) {
                //LLog.d(TAG, "onNetworkChange")
                LSharedPrefsUtil.instance.putBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, isConnected)
                EventBusData.instance.sendConnectChange(isConnected = true, isConnectedFast = isConnectedFast, isConnectedWifi = isConnectedWifi, isConnectedMobile = isConnectedMobile)
            }
        } else {
            //LLog.d(TAG, "!isConnected")
            val prevIsConnectedNetwork = LSharedPrefsUtil.instance.getBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK)
            //LLog.d(TAG, "prevIsConnectedNetwork $prevIsConnectedNetwork")
            if (prevIsConnectedNetwork != isConnected) {
                //LLog.d(TAG, "onNetworkChange")
                LSharedPrefsUtil.instance.putBoolean(LSharedPrefsUtil.KEY_BOOLEAN_IS_CONNECTED_NETWORK, isConnected)
                EventBusData.instance.sendConnectChange(isConnected = false, isConnectedFast = false,
                        isConnectedWifi = false, isConnectedMobile = false)
            }
        }
    }
}
