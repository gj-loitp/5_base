package com.loitp.core.utilities

import android.annotation.SuppressLint
import com.loitp.R
import com.loitp.core.common.Constants
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Suppress("unused")
class LFCMUtil {
    companion object {
        private val logTag = LFCMUtil::class.java.simpleName
        private val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        fun testCrash() {
            throw RuntimeException(
                "FIREBASE CRASHLYTICS TEST::" + DateFormat.getDateTimeInstance().format(Date())
            )
        }

        @SuppressLint("CheckResult")
        fun sendNotification(
            key: String,
            body: String,
            onComplete: ((Unit) -> Unit)?,
            onError: ((Unit) -> Unit)?
        ) {
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
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onComplete?.invoke(Unit)
                }, {
                    onError?.invoke(Unit)
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
