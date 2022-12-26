package com.loitp.task

import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class GetPdfCoroutine : CoroutineScope {
    //    private val logTag = javaClass.simpleName
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
    }

    fun startTask(
        urlPdf: String,
        folderPath: String,
        folderName: String,
        resultPercent: (Float?) -> Unit,
        resultFile: (File?) -> Unit
    ) = launch {
        val file = doInBackground(
            urlPdf = urlPdf,
            folderPath = folderPath,
            folderName = folderName,
            resultPercent = { percent ->
                resultPercent.invoke(percent)
            }
        )
        resultFile.invoke(file)
    }

    private suspend fun doInBackground(
        urlPdf: String,
        folderPath: String,
        folderName: String,
        resultPercent: (Float?) -> Unit
    ): File? = withContext(Dispatchers.IO) {
//        val startTime = System.currentTimeMillis()
        val fileName = try {
            val arr: Array<String> = urlPdf.split("/").toTypedArray()
            arr[arr.size - 1]
        } catch (e: java.lang.Exception) {
            urlPdf
        }
        val file = downloadFileToSdCard(
            urlPdf = urlPdf,
            folderPath = folderPath,
            folderName = folderName,
            fileName = fileName,
            percentResult = { percent ->
                resultPercent.invoke(percent)
            }
        )
        return@withContext file
    }

    private fun downloadFileToSdCard(
        urlPdf: String,
        folderPath: String,
        folderName: String,
        fileName: String,
        percentResult: (Float?) -> Unit
    ): File? {
        return try {
            val url = URL(urlPdf)
            val dir = File("$folderPath/$folderName")
            if (!dir.exists()) {
                val isMkDirResult: Boolean = dir.mkdir()
                print("isMkDirResult $isMkDirResult")
            }
            /* checks the file and if it already exist delete */
            val file = File(dir, fileName)
            if (file.exists()) {
                val isDeleted: Boolean = file.delete()
                print("isDeleted $isDeleted")
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
            var downloadedSize = 0
            val buffer = ByteArray(1024 * 2)
            var bufferLength: Int
            while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                if (job.isCancelled) {
                    if (file.exists()) {
                        val isDelete = file.delete()
                        print("isDelete $isDelete")
                    }
                    return null
                }
                fos.write(buffer, 0, bufferLength)
                downloadedSize += bufferLength
                val percent = downloadedSize.toFloat() * 100 / totalSize.toFloat()
                percentResult.invoke(percent)
            }
            fos.close()
            file
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
}
