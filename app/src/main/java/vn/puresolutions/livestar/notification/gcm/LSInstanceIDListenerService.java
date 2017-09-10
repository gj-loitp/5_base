package vn.puresolutions.livestar.notification.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;


/**
 * Created by Khanh on 8/26/15.
 */
public class LSInstanceIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, LSRegistrationIntentService.class);
        startService(intent);
    }
}