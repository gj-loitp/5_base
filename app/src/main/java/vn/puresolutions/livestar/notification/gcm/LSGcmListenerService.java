package vn.puresolutions.livestar.notification.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.notification.handler.NotificationHandler;


/**
 * Created by Khanh on 8/26/15.
 */
public class LSGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (from.equals(getString(R.string.gcm_sender_id))) {
            new NotificationHandler(this)
                    .pushNotification(data);
        }
    }
}