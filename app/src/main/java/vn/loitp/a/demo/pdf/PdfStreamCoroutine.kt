package vn.loitp.a.demo.pdf

import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.coroutines.CoroutineContext

class PdfStreamCoroutine : CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
    }

    fun startTask(
        urlPdf: String,
        result: (InputStream?) -> Unit
    ) = launch {
        val inputStream = doInBackground(urlPdf)
        result.invoke(inputStream)
    }

    private suspend fun doInBackground(urlPdf: String): InputStream? = withContext(Dispatchers.IO) {
        // to run code in Background Thread
        // do async work
        var inputStream: InputStream? = null
        var url: URL? = null
        try {
            url = URL(urlPdf)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
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
