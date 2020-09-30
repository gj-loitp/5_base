package vn.loitp.app.activity.demo.pdf

import com.core.utilities.LLog
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Created by Loitp on 10,January,2020
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class PdfStreamCoroutine : CoroutineScope {
    private val logTag = javaClass.simpleName
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
        LLog.d(logTag, "cancel " + job.isCancelled)
    }

    fun startTask(urlPdf: String, result: (InputStream?) -> Unit) = launch {
        LLog.d(logTag, "startTask")
        val inputStream = doInBackground(urlPdf)
        result.invoke(inputStream)
    }

    private suspend fun doInBackground(urlPdf: String): InputStream? = withContext(Dispatchers.IO) {
        // to run code in Background Thread
        // do async work
        LLog.d(logTag, "doInBackground")
        var inputStream: InputStream? = null
        var url: URL? = null
        try {
            url = URL(urlPdf)
        } catch (e: MalformedURLException) {
            LLog.e(logTag, "doInBackground MalformedURLException $e")
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
            e.printStackTrace()
            return@withContext null
        }

        return@withContext inputStream
    }

}
