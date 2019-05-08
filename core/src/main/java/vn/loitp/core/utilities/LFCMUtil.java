package vn.loitp.core.utilities;

/**
 * Created by LENOVO on 6/7/2018.
 */

import android.os.AsyncTask;

import org.json.JSONObject;

import loitp.core.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.loitp.core.common.Constants;

/**
 * Created by HOME on 4/18/2017.
 */

public class LFCMUtil {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendNotification(final String key, final String body) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("body", body);
                    dataJson.put("title", R.string.app_name);
                    json.put("notification", dataJson);
                    json.put("to", Constants.INSTANCE.getFCM_TOPIC());
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    //LLog.d(TAG, "body:" + LApplication.getGson().toJson(body));
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + key)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    //LLog.d(TAG, "finalResponse:" + LApplication.getGson().toJson(finalResponse));
                } catch (Exception e) {
                    //LLog.d(TAG, "Exception " + e.toString());
                }
                return null;
            }
        }.execute();
    }
}
