package com.task

import com.core.utilities.LLog
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Created by Loitp on 10,January,2020
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class GetPdfCoroutine : CoroutineScope {
    private val TAG = "loitppp" + javaClass.simpleName
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
        LLog.d(TAG, "cancel " + job.isCancelled)
    }

    fun startTask(urlPdf: String, folderPath: String, folderName: String, result: (File?) -> Unit) = launch {
        LLog.d(TAG, "startTask urlPdf: $urlPdf")
        LLog.d(TAG, "startTask folderPath: $folderPath")
        LLog.d(TAG, "startTask folderName: $folderName")
        val file = doInBackground(urlPdf = urlPdf, folderPath = folderPath, folderName = folderName)
        result.invoke(file)
    }

    private suspend fun doInBackground(urlPdf: String, folderPath: String, folderName: String): File? = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        LLog.d(TAG, "doInBackground startTime: $startTime")
        val fileName = try {
            val arr: Array<String> = urlPdf.split("/").toTypedArray()
            arr[arr.size - 1]
        } catch (e: java.lang.Exception) {
            urlPdf
        }
        LLog.d(TAG, "doInBackground fileName: $fileName")
        val file = downloadFileToSdCard(urlPdf = urlPdf, folderPath = folderPath, folderName = folderName, fileName = fileName)
        return@withContext file
    }

    private fun downloadFileToSdCard(urlPdf: String, folderPath: String, folderName: String, fileName: String): File? {
        return try {
            val url = URL(urlPdf)
            val dir = File(folderPath + "/" + folderName)
            if (!dir.exists()) {
                val isMkDirResult: Boolean = dir.mkdir()
                LLog.d(TAG, "downloadFileToSdCard isMkDirResult $isMkDirResult")
            }
            /* checks the file and if it already exist delete */
            val file = File(dir, fileName)
            if (file.exists()) {
                val isDeleted: Boolean = file.delete()
                LLog.d(TAG, "downloadFileToSdCard isDeleted $isDeleted")
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
                LLog.e(TAG, "downloadFileToSdCard downloadFileToSdCard -> return")
                return null
            }
            val fos = FileOutputStream(file)
            val totalSize = httpURLConnection.contentLength
            LLog.d(TAG, "totalSize $totalSize")
            var downloadedSize = 0
            val buffer = ByteArray(1024 * 2)
            var bufferLength: Int
            while (inputStream.read(buffer).also { bufferLength = it } > 0) {

                //TODO
                /*if (isCancelled()) {
                    LLog.d(TAG, "isCancelled -> return false")
                    return null
                }*/


                fos.write(buffer, 0, bufferLength)
                downloadedSize += bufferLength
                publishProgress(downloadedSize, totalSize)
            }
            fos.close()
            LLog.d(TAG, "File saved in sdcard..")
            file
        } catch (ioException: IOException) {
            //exception = ioException
            LLog.e(TAG, "IOException$ioException")
            null
        } catch (e: java.lang.Exception) {
            //exception = e
            LLog.e(TAG, "Exception$e")
            null
        }
    }

    private fun publishProgress(downloadedSize: Int, totalSize: Int) {
        val percent = downloadedSize.toFloat() * 100 / totalSize.toFloat()
        LLog.d(TAG, "onProgressUpdate downloadedSize: $downloadedSize totalSize: $totalSize -> percent: $percent%")
        /*if (callback != null) {
            callback.onProgressUpdate(downloadedSize, totalSize, percent)
        }*/
    }

    interface Callback {
        fun onSuccess(durationSec: Long, durationHHmmss: String?, file: File?)
        fun onError(e: Exception?)
        fun onProgressUpdate(downloadedSize: Int, totalSize: Int, percent: Float)
    }
}
