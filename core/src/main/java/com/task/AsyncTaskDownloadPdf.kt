package com.task

import android.os.AsyncTask
import com.core.utilities.LDateUtil.Companion.convertSToFormat
import com.core.utilities.LLog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskDownloadPdf(
        private val folderPath: String,
        private val mURL: String,
        folderName: String,
        callback: Callback?
) : AsyncTask<String, Int, File>() {

    interface Callback {
        fun onSuccess(durationSec: Long, durationHHmmss: String?, file: File?)
        fun onError(e: Exception?)
        fun onProgressUpdate(downloadedSize: Int, totalSize: Int, percent: Float)
    }

    private val logTag = javaClass.simpleName
    private var fileName: String = System.currentTimeMillis().toString()
    private val folderName: String
    private var startTime: Long = 0
    private val callback: Callback?
    private var exception: Exception? = null

    init {
        fileName = try {
            val arr = mURL.split("/".toRegex()).toTypedArray()
            arr[arr.size - 1]
        } catch (e: Exception) {
            mURL
        }
        this.folderName = folderName
        this.callback = callback
    }

    override fun onPreExecute() {
        super.onPreExecute()
        startTime = System.currentTimeMillis()
    }

    override fun doInBackground(vararg params: String): File? {
        return downloadFileToSdCard(downloadUrl = mURL, fileName = fileName)
    }

    private fun downloadFileToSdCard(downloadUrl: String, fileName: String): File? {
        return try {
            val url = URL(downloadUrl)
            val dir = File("$folderPath/$folderName")
            if (!dir.exists()) {
                val isMkDirResult = dir.mkdir()
                LLog.d(logTag, "isMkDirResult $isMkDirResult")
            }

            /* checks the file and if it already exist delete */
            val file = File(dir, fileName)
            if (file.exists()) {
                val isDeleted = file.delete()
                LLog.d(logTag, "isDeleted $isDeleted")
            }

            /* Open a connection */
            val urlConnection = url.openConnection()
            var inputStream: InputStream? = null
            val httpURLConnection = urlConnection as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.inputStream
            }
            if (inputStream == null) {
                return null
            }
            val fos = FileOutputStream(file)
            val totalSize = httpURLConnection.contentLength
            LLog.d(logTag, "totalSize $totalSize")
            var downloadedSize = 0
            val buffer = ByteArray(1024 * 2)
            var bufferLength: Int
            while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                if (isCancelled) {
                    LLog.d(logTag, "isCancelled -> return false")
                    return null
                }
                fos.write(buffer, 0, bufferLength)
                downloadedSize += bufferLength
                publishProgress(downloadedSize, totalSize)
            }
            fos.close()
            LLog.d(logTag, "File saved in sdcard..")
            file
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            exception = ioException
            LLog.e(logTag, "IOException $ioException")
            null
        } catch (e: Exception) {
            e.printStackTrace()
            exception = e
            LLog.e(logTag, "Exception $e")
            null
        }
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)

        val downloadedSize = values[0]
        val totalSize = values[1]

        downloadedSize?.let { dS ->
            totalSize?.let { tS ->
                val percent = dS.toFloat() * 100 / tS.toFloat()
                LLog.d(logTag, "onProgressUpdate: " + dS + "-" + tS + " -> " + (dS * 100 / tS) + "%")
                callback?.onProgressUpdate(downloadedSize = downloadedSize, totalSize = totalSize, percent = percent)
            }
        }
    }

    override fun onPostExecute(file: File?) {
        val isDownloaded = file != null && file.exists()
        LLog.d(logTag, "onPostExecute isDownloaded: $isDownloaded")
        if (isDownloaded) {
            if (callback != null) {
                val endTime = System.currentTimeMillis()
                val durationSec = (endTime - startTime) / 1000
                val duration = convertSToFormat(durationSec, "HH:mm:ss")
                LLog.d(logTag, "onPostExecute duration: $duration")
                callback.onSuccess(durationSec, duration, file)
            }
        } else {
            callback?.onError(exception)
        }
        super.onPostExecute(file)
    }

}
