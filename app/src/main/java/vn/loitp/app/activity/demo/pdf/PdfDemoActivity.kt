package vn.loitp.app.activity.demo.pdf

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LStoreUtil
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.task.AsyncTaskDownloadPdf
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_pdf_demo.*
import loitp.basemaster.R
import java.io.File

//https://github.com/barteksc/AndroidPdfViewer
class PdfDemoActivity : BaseFontActivity() {
    private var asyncTaskDownloadPdf: AsyncTaskDownloadPdf? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btFile.setSafeOnClickListener {
            fromUrl()
        }
        btStream.setSafeOnClickListener {
            //RetrievePDFStream().execute("http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf")
            //RetrievePDFStream().execute("http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf");
            pb.visibility = View.VISIBLE
            val pdfCoroutine = PdfCoroutine()
            pdfCoroutine.startTask(urlPdf = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf",
                    result = { inputStream ->
                        pdfView.visibility = View.VISIBLE
                        pdfView.fromStream(inputStream).load()
                        pb.visibility = View.GONE
                    })
        }

        showDialogMsg(errMsg = "You can load pdf from url, uri, file, asset, bytes, stream...", runnable = null)
    }


    /*private inner class RetrievePDFStream : AsyncTask<String, Void, InputStream>() {
        override fun doInBackground(vararg strings: String): InputStream? {
            LLog.d(TAG, "doInBackground")
            var inputStream: InputStream? = null
            var url: URL? = null
            try {
                url = URL(strings[0])
            } catch (e: MalformedURLException) {
                LLog.e(TAG, "doInBackground MalformedURLException $e")
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
                LLog.d(TAG, "doInBackground IOException $e")
                return null
            }

            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream) {
            LLog.d(TAG, "onPostExecute")

            pdfView.visibility = View.VISIBLE
            pdfView.fromStream(inputStream).load()
        }
    }*/

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pdf_demo
    }

    override fun onDestroy() {
        asyncTaskDownloadPdf?.cancel(true)
        super.onDestroy()
    }

    private fun fromUrl() {
        asyncTaskDownloadPdf?.cancel(true)
        val folderPath = LStoreUtil.getFolderPath(activity, "ZZZDemoPDF")
        //final String url = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
        //final String url = "http://www.pdf995.com/samples/pdf.pdf";
        //final String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        val url = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
        val folderName = "PDFDemo"
        pb.visibility = View.VISIBLE
        pb.progress = 0
        pdfView.visibility = View.GONE
        asyncTaskDownloadPdf = AsyncTaskDownloadPdf(folderPath, url, folderName, object : AsyncTaskDownloadPdf.Callback {

            override fun onSuccess(durationSec: Long, durationHHmmss: String, file: File) {
                LLog.d(TAG, "onSuccess $durationSec - $durationHHmmss")
                LLog.d(TAG, "onSuccess " + file.path)
                showShort("onSuccess after $durationSec seconds")
                pb.visibility = View.GONE
                pdfView.visibility = View.VISIBLE
                btFile.visibility = View.GONE
                showPDF(file)
            }

            override fun onError(e: Exception) {
                LLog.d(TAG, "onError")
                pb.visibility = View.GONE
            }

            override fun onProgressUpdate(downloadedSize: Int, totalSize: Int, percent: Float) {
                LLog.d(TAG, "onProgressUpdate $downloadedSize - $totalSize - $percent")
                pb.progress = percent.toInt()
            }
        })
        asyncTaskDownloadPdf?.execute()
    }

    private fun showPDF(file: File) {
        //Option 1: OK
        //        pdfView.fromFile(file)
        //                .defaultPage(0)
        //                .enableAnnotationRendering(true)
        //                .load();

        //Option 2 vertical scroll
        //        pdfView.fromFile(file)
        //                //.pages(0) // all pages are displayed by default
        //                .enableSwipe(true) // allows to block changing pages using swipe
        //                .swipeHorizontal(false)
        //                .enableDoubletap(true)
        //                .defaultPage(0)
        //                .onLoad(nbPages -> LLog.d(TAG, "loadComplete " + nbPages)) // called after document is loaded and starts to be rendered
        //                .onPageChange((page, pageCount) -> {
        //                    LLog.d(TAG, "onPageChange " + page + "/" + pageCount);
        //                })
        //                .onPageScroll((page, positionOffset) -> LLog.d(TAG, "onPageScrolled " + page + " - " + positionOffset))
        //                .onError(t -> LLog.e(TAG, "onError " + t.toString()))
        //                .onPageError((page, t) -> LLog.e(TAG, "onPageError " + page + " -> " + t.toString()))
        //                .onRender(nbPages -> LLog.d(TAG, "onInitiallyRendered nbPages " + nbPages)) // called after document is rendered for the first time
        //                // called on single tap, return true if handled, false to toggle scroll handle visibility
        //                .onTap(e -> {
        //                    LLog.d(TAG, "onTap");
        //                    return false;
        //                })
        //                .onLongPress(e -> LLog.d(TAG, "OnLongPressListener"))
        //                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
        //                .password(null)
        //                .scrollHandle(new DefaultScrollHandle(this))
        //                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
        //                // spacing between pages in dp. To define spacing color, set view background
        //                .spacing(0)
        //                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
        //                .pageFitPolicy(FitPolicy.WIDTH)
        //                .pageSnap(true) // snap pages to screen boundaries
        //                .pageFling(false) // make a fling change only a single page like ViewPager
        //                .nightMode(false) // toggle night parrallaxMode
        //                .load();


        //Option 3 horizontal scroll
        pdfView.fromFile(file)
                //.pages(0) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .onLoad { nbPages -> LLog.d(TAG, "loadComplete $nbPages") } // called after document is loaded and starts to be rendered
                .onPageChange { page, pageCount -> LLog.d(TAG, "onPageChange $page/$pageCount") }
                .onPageScroll { page, positionOffset -> LLog.d(TAG, "onPageScrolled $page - $positionOffset") }
                .onError { t -> LLog.e(TAG, "onError $t") }
                .onPageError { page, t -> LLog.e(TAG, "onPageError $page -> $t") }
                .onRender { nbPages -> LLog.d(TAG, "onInitiallyRendered nbPages $nbPages") } // called after document is rendered for the first time
                // called on single tap, return true if handled, false to toggle scroll handle visibility
                .onTap { e ->
                    LLog.d(TAG, "onTap")
                    false
                }
                .onLongPress { e -> LLog.d(TAG, "OnLongPressListener") }
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(DefaultScrollHandle(this))
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night parrallaxMode
                .load()
    }

    override fun onBackPressed() {
        if (pdfView.visibility == View.VISIBLE) {
            pdfView.visibility = View.GONE
            btFile.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}
