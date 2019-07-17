package com.core.utilities

import com.core.common.Constants
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
    private val TAG = javaClass.simpleName
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    fun sendNotification(key: String, body: String) {
        val disposable: Disposable = Completable.fromAction {
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
            val finalResponse = response.body()
            LLog.d(TAG, "finalResponse:" + finalResponse.toString())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    LLog.d(TAG, "onComplete")
                }, {
                    LLog.d(TAG, "onError $it")
                })
    }
}
