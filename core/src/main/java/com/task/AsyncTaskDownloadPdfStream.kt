package com.task

import android.os.AsyncTask
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class AsyncTaskDownloadPdfStream(val result: (InputStream?) -> Unit) : AsyncTask<String, Void, InputStream>() {
    private val logTag = javaClass.simpleName

    override fun doInBackground(vararg strings: String): InputStream? {
        var inputStream: InputStream? = null
        var url: URL? = null
        try {
            url = URL(strings[0])
        } catch (e: MalformedURLException) {
            e.printStackTrace()
//            Log.e(logTag, "doInBackground MalformedURLException $e")
        }

        try {
            var httpURLConnection: HttpURLConnection? = null
            if (url != null) {
                httpURLConnection = url.openConnection() as HttpURLConnection
            }
            if (httpURLConnection != null && httpURLConnection.responseCode == 200) {
                inputStream = BufferedInputStream(httpURLConnection.inputStream)
            }
        } catch (e: IOException) {
//            Log.e(logTag, "doInBackground IOException $e")
            return null
        }

        return inputStream
    }

    override fun onPostExecute(inputStream: InputStream) {
//        Log.d(logTag, "onPostExecute")
        result.invoke(inputStream)
    }
}