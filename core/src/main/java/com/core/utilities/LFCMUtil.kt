package com.core.utilities

import android.os.AsyncTask
import com.core.common.Constants
import loitp.core.R
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * Created by Loitp on 4/18/2017.
 */

object LFCMUtil {
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    fun sendNotification(key: String, body: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                try {
                    val client = OkHttpClient()
                    val json = JSONObject()
                    val dataJson = JSONObject()
                    dataJson.put("body", body)
                    dataJson.put("title", R.string.app_name)
                    json.put("notification", dataJson)
                    json.put("to", Constants.FCM_TOPIC)
                    val body = RequestBody.create(JSON, json.toString())
                    //LLog.d(TAG, "body:" + LApplication.getGson().toJson(body));
                    val request = Request.Builder()
                            .header("Authorization", "key=$key")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build()
                    val response = client.newCall(request).execute()
                    //String finalResponse = response.body().string();
                    //LLog.d(TAG, "finalResponse:" + LApplication.getGson().toJson(finalResponse));
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return null
            }
        }.execute()
    }
}
