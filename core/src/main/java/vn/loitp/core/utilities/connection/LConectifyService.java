package vn.loitp.core.utilities.connection;

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.data.EventBusData;

/**
 * Created by Loitp on 5/6/2017.
 */

/*
public class LConectifyService extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        if (LConnectivityUtil.isConnected(context)) {
            boolean isConnectedMobile = false;
            boolean isConnectedWifi = false;
            boolean isConnectedFast = false;
            if (LConnectivityUtil.isConnectedMobile(context)) {
                //LLog.d(TAG, "isConnectedMobile");
                isConnectedMobile = true;
            }
            if (LConnectivityUtil.isConnectedWifi(context)) {
                //LLog.d(TAG, "isConnectedWifi");
                isConnectedWifi = true;
            }
            if (LConnectivityUtil.isConnectedFast(context)) {
                LLog.d(TAG, "isConnectedFast");
                isConnectedFast = true;
            }
            EventBusData.getInstance().sendConnectChange(true, isConnectedFast, isConnectedWifi, isConnectedMobile);
        } else {
            LLog.d(TAG, "!isConnected");
            EventBusData.getInstance().sendConnectChange(false, false, false, false);
        }
    }
}*/


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LConectifyService extends JobService implements ConnectivityReceiver.ConnectivityReceiverListener {

    private final String TAG = LConectifyService.class.getSimpleName();

    private ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        LLog.d(TAG, "Service created");
        mConnectivityReceiver = new ConnectivityReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LLog.d(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        LLog.d(TAG, "onStartJob" + mConnectivityReceiver);
        //registerReceiver(mConnectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LLog.d(TAG, "onStopJob");
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        //LLog.d(TAG, "onNetworkConnectionChanged " + message);
        if (isConnected) {
            boolean isConnectedMobile = false;
            boolean isConnectedWifi = false;
            boolean isConnectedFast = false;
            if (LConnectivityUtil.isConnectedMobile(this)) {
                LLog.d(TAG, "isConnectedMobile");
                isConnectedMobile = true;
            }
            if (LConnectivityUtil.isConnectedWifi(this)) {
                LLog.d(TAG, "isConnectedWifi");
                isConnectedWifi = true;
            }
            if (LConnectivityUtil.isConnectedFast(this)) {
                LLog.d(TAG, "isConnectedFast");
                isConnectedFast = true;
            }
            EventBusData.getInstance().sendConnectChange(true, isConnectedFast, isConnectedWifi, isConnectedMobile);
        } else {
            LLog.d(TAG, "!isConnected");
            EventBusData.getInstance().sendConnectChange(false, false, false, false);
        }
    }
}
