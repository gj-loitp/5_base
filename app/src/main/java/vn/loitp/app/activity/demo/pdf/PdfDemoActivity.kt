package vn.loitp.app.activity.demo.pdf

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LStoreUtil
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.task.AsyncTaskDownloadPdf
import com.task.AsyncTaskDownloadPdfStream
import com.task.GetPdfCoroutine
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_pdf_demo.*
import loitp.basemaster.R
import java.io.File

//https://github.com/barteksc/AndroidPdfViewer
class PdfDemoActivity : BaseFontActivity() {
    private var asyncTaskDownloadPdf: AsyncTaskDownloadPdf? = null
    private var asyncTaskDownloadPdfStream: AsyncTaskDownloadPdfStream? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "loitppp" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pdf_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btFileAsynctask.setSafeOnClickListener {
            callAysnctaskFile()
        }
        btFileCoroutine.setSafeOnClickListener {
            //TODO
            val urlPdf = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
            //val urlPdf = "http://www.pdf995.com/samples/pdf.pdf";
            //val urlPdf = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
            //val urlPdf = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
            val folderPath = LStoreUtil.getFolderPath(activity, "ZZZDemoPDF")
            val folderName = "PDFDemo"
            GetPdfCoroutine().startTask(urlPdf, folderPath, folderName) { file ->
                LLog.d(TAG, "GetPdfTask ${file?.path}")
            }
        }
        btStreamAsyncTask.setSafeOnClickListener {
            callAysnctaskStream()
        }
        btStreamCoroutine.setSafeOnClickListener {
            //TODO
//            pb.visibility = View.VISIBLE
//            pb.progress = 0
//            val pdfCoroutine = PdfCoroutine()
//            pdfCoroutine.startTask(urlPdf = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf",
//                    result = { inputStream ->
//                        pdfView.visibility = View.VISIBLE
//                        pdfView.fromStream(inputStream).load()
//                        pb.visibility = View.GONE
//                    })
        }
        showDialogMsg(errMsg = "You can load pdf from url, uri, file, asset, bytes, stream...", runnable = null)
    }

    override fun onDestroy() {
        asyncTaskDownloadPdf?.cancel(true)
        asyncTaskDownloadPdfStream?.cancel(true)
        super.onDestroy()
    }

    private fun callAysnctaskFile() {
        asyncTaskDownloadPdf?.cancel(true)
        val url = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
        //val url = "http://www.pdf995.com/samples/pdf.pdf";
        //val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        //val url = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
        val folderPath = LStoreUtil.getFolderPath(activity, "ZZZDemoPDF")
        val folderName = "PDFDemo"
        pb.visibility = View.VISIBLE
        pb.progress = 0
        pdfView.visibility = View.GONE
        btFileAsynctask.visibility = View.GONE
        btFileCoroutine.visibility = View.GONE
        btStreamAsyncTask.visibility = View.GONE
        btStreamCoroutine.visibility = View.GONE
        asyncTaskDownloadPdf = AsyncTaskDownloadPdf(folderPath, url, folderName, object : AsyncTaskDownloadPdf.Callback {

            override fun onSuccess(durationSec: Long, durationHHmmss: String, file: File) {
                LLog.d(TAG, "onSuccess $durationSec - $durationHHmmss")
                LLog.d(TAG, "onSuccess " + file.path)
                showShort("onSuccess after $durationSec seconds")
                pb.visibility = View.GONE
                pdfView.visibility = View.VISIBLE
                showPDF(file)
                btFileAsynctask.visibility = View.VISIBLE
                btFileCoroutine.visibility = View.VISIBLE
                btStreamAsyncTask.visibility = View.VISIBLE
                btStreamCoroutine.visibility = View.VISIBLE
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

    private fun callAysnctaskStream() {
        asyncTaskDownloadPdfStream?.cancel(true)
        //val url = "http://www.pdf995.com/samples/pdf.pdf";
        //val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        //val url = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
        pb.visibility = View.VISIBLE
        pb.progress = 0
        pdfView.visibility = View.GONE
        btFileAsynctask.visibility = View.GONE
        btFileCoroutine.visibility = View.GONE
        btStreamAsyncTask.visibility = View.GONE
        btStreamCoroutine.visibility = View.GONE
        asyncTaskDownloadPdfStream = AsyncTaskDownloadPdfStream(result = { inputStream ->
            pdfView?.let {
                it.visibility = View.VISIBLE
                it.fromStream(inputStream).load()
                pb.visibility = View.GONE
                btFileAsynctask.visibility = View.VISIBLE
                btFileCoroutine.visibility = View.VISIBLE
                btStreamAsyncTask.visibility = View.VISIBLE
                btStreamCoroutine.visibility = View.VISIBLE
            }
        })
        asyncTaskDownloadPdfStream?.execute("http://www.pdf995.com/samples/pdf.pdf")
        //asyncTaskDownloadPdfStream?.execute("http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf")
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
}
