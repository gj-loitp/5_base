package vn.puresolutions.livestar.notification.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import rx.Subscription;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.param.DeviceParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.DeviceService;
import vn.puresolutions.livestarv3.utilities.old.DeviceUtils;

/**
 * Created by Khanh on 8/26/15.
 */
public class LSRegistrationIntentService extends IntentService {
    private static final String TAG = "Notification";

    private Subscription subscription;

    public LSRegistrationIntentService() {
        super("LSRegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String senderId = getString(R.string.gcm_sender_id);
            String token = instanceID.getToken(senderId,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            sendRegistrationToServer(token);
            Log.i(TAG, "GCM Registration Token: " + token);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
        }
    }

    private void sendRegistrationToServer(String token) {
        DeviceParam param = new DeviceParam(token, DeviceUtils.getUniquePsuedoID());
        try {
            DeviceService service = RestClient.createService(DeviceService.class);
            subscription = service.register(param).subscribe(result -> {
                Log.i(TAG, "Submit token successfully");
            });
        } catch (Exception e) {
            Log.e(TAG, "Submit token failed", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
