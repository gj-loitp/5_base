package vn.loitp.core.utilities;

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import loitp.core.R;

/**
 * Created by Loitp on 5/6/2017.
 */

public class LConectifyBroadcast extends BroadcastReceiver {
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
                LLog.d(TAG, "isConnectedMobile");
                isConnectedMobile = true;
            }
            if (LConnectivityUtil.isConnectedWifi(context)) {
                LLog.d(TAG, "isConnectedWifi");
                isConnectedWifi = true;
            }
            if (LConnectivityUtil.isConnectedFast(context)) {
                LLog.d(TAG, "isConnectedFast");
                isConnectedFast = true;
            }
            //EventBusData.getInstance().sendConnectChange(true, isConnectedFast, isConnectedWifi, isConnectedMobile);
        } else {
            LLog.d(TAG, "!isConnected");
            //EventBusData.getInstance().sendConnectChange(false, false, false, false);
        }
    }
}