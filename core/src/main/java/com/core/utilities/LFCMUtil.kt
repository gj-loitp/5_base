package com.core.utilities

import android.annotation.SuppressLint
import com.R
import com.core.common.Constants
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Created by Loitp on 4/18/2017.
 */

class LFCMUtil {
    companion object {
        private val TAG = LFCMUtil::class.java.simpleName
        private val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        @SuppressLint("CheckResult")
        fun sendNotification(key: String, body: String) {
            Completable.fromAction {
                val client = OkHttpClient()
                val json = JSONObject()
                val dataJson = JSONObject()
                dataJson.put("body", body)
                dataJson.put("title", R.string.app_name)
                json.put("notification", dataJson)
                json.put("to", Constants.FCM_TOPIC)
                val jsonBody = json.toString().toRequestBody(JSON)
                val request = Request.Builder()
                        .header("Authorization", "key=$key")
                        .url("https://fcm.googleapis.com/fcm/send")
                        .post(jsonBody)
                        .build()
                val response = client.newCall(request).execute()
                val finalResponse = response.body
                LLog.d(TAG, "finalResponse:" + finalResponse.toString())
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        LLog.d(TAG, "onComplete")
                    }, {
                        LLog.d(TAG, "onError $it")
                    })
        }

//        fun getFCMToken(context: Context) {
//            FirebaseInstanceId.getInstance().instanceId
//                    .addOnCompleteListener(OnCompleteListener { task ->
//                        if (!task.isSuccessful) {
//                            return@OnCompleteListener
//                        }
//                        val fcmToken = task.result?.token
//                        VinPref.setFCMToken(fcmToken)
//                    })
//        }

//        fun resetInstanceId() {
//            Thread(Runnable {
//                try {
//                    FirebaseInstanceId.getInstance().deleteInstanceId()
//                    FirebaseInstanceId.getInstance().instanceId
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }).start()
//        }
    }
}
