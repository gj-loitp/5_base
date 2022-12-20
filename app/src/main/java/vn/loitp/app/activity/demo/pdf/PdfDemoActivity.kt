package vn.loitp.app.activity.demo.pdf

import android.os.Bundle
import android.view.View
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.huxq17.download.Pump
import com.huxq17.download.core.DownloadListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.task.AsyncTaskDownloadPdfStream
import com.loitp.task.GetPdfCoroutine
import kotlinx.android.synthetic.main.activity_demo_pdf.*
import vn.loitp.app.R
import java.io.File

// https://github.com/barteksc/AndroidPdfViewer
@LogTag("PdfDemoActivity")
@IsFullScreen(false)
class PdfDemoActivity : BaseFontActivity() {
    private var asyncTaskDownloadPdfStream: AsyncTaskDownloadPdfStream? = null
    private var getPdfCoroutine: GetPdfCoroutine? = null
    private var pdfStreamCoroutine: PdfStreamCoroutine? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_pdf
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = PdfDemoActivity::class.java.simpleName
        }
        btFileAsyncTask.setSafeOnClickListener {
            callAsyncTaskFile()
        }
        btFileCoroutine.setSafeOnClickListener {
            //TODO deo chay :((
            callCoroutineFile()
        }
        btStreamAsyncTask.setSafeOnClickListener {
            callAsyncTaskStream()
        }
        btStreamCoroutine.setSafeOnClickListener {
            callCoroutineStream()
        }
        showDialogMsg(
            errMsg = "You can load pdf from url, uri, file, asset, bytes, stream...",
            runnable = null
        )
    }

    override fun onDestroy() {
        asyncTaskDownloadPdfStream?.cancel(true)
        getPdfCoroutine?.cancel()
        super.onDestroy()
    }

    private fun updateUIProgress(isLoading: Boolean) {
        if (isLoading) {
            pb.visibility = View.VISIBLE
            pb.progress = 0
            pdfView.visibility = View.GONE
            btFileAsyncTask.visibility = View.GONE
            btFileCoroutine.visibility = View.GONE
            btStreamAsyncTask.visibility = View.GONE
            btStreamCoroutine.visibility = View.GONE
        } else {
            pb.visibility = View.GONE
            btFileAsyncTask.visibility = View.VISIBLE
            btFileCoroutine.visibility = View.VISIBLE
            btStreamAsyncTask.visibility = View.VISIBLE
            btStreamCoroutine.visibility = View.VISIBLE
        }
    }

    private fun callAsyncTaskFile() {
        updateUIProgress(isLoading = true)
        Pump.newRequestToDownload(
            "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf",
            "/loitp/pdf"
        )
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                    pb.progress = progress
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.downloadFile.file
                    showShortInformation("Download Finished ${downloadInfo?.filePath}")

                    pdfView.visibility = View.VISIBLE
                    filePath?.let {
                        showPDF(file = filePath)
                    }
                    updateUIProgress(isLoading = false)
                }

                override fun onFailed() {
                    showShortError("Download failed")
                    updateUIProgress(isLoading = false)
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }

    private fun callAsyncTaskStream() {
        asyncTaskDownloadPdfStream?.cancel(true)
        // val url = "http://www.pdf995.com/samples/pdf.pdf";
        // val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        // val url = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
        updateUIProgress(isLoading = true)
        btStreamCoroutine.visibility = View.GONE
        asyncTaskDownloadPdfStream = AsyncTaskDownloadPdfStream(result = { inputStream ->
            pdfView?.let {
                it.visibility = View.VISIBLE
                it.fromStream(inputStream).load()
            }
            updateUIProgress(isLoading = false)
        })
        asyncTaskDownloadPdfStream?.execute("http://www.pdf995.com/samples/pdf.pdf")
        // asyncTaskDownloadPdfStream?.execute("http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf")
    }

    private fun callCoroutineFile() {
        updateUIProgress(isLoading = true)
        val urlPdf = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf"
        // val urlPdf = "http://www.pdf995.com/samples/pdf.pdf";
        // val urlPdf = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        // val urlPdf = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf"
        val folderPath = LStoreUtil.getFolderPath(folderName = "ZZZDemoPDF")
        val folderName = "PDFDemo"
        getPdfCoroutine = GetPdfCoroutine()
        getPdfCoroutine?.startTask(
            urlPdf = urlPdf,
            folderPath = folderPath,
            folderName = folderName,
            resultPercent = { percent ->
                percent?.let {
                    pb.progress = it.toInt()
                }
            },
            resultFile = { file ->
                logD("GetPdfTask ${file?.path}")
                pdfView.visibility = View.VISIBLE
                file?.let { f ->
                    showPDF(f)
                }
                updateUIProgress(isLoading = false)
            }
        )
    }

    private fun callCoroutineStream() {
        updateUIProgress(isLoading = true)
        pdfStreamCoroutine = PdfStreamCoroutine()
        pdfStreamCoroutine?.startTask(
            urlPdf = "http://www.pdf995.com/samples/pdf.pdf",
            result = { inputStream ->
                pdfView.visibility = View.VISIBLE
                pdfView.fromStream(inputStream).load()
                updateUIProgress(isLoading = false)
            }
        )
    }

    private fun showPDF(file: File) {
        // Option 1: OK
        //        pdfView.fromFile(file)
        //                .defaultPage(0)
        //                .enableAnnotationRendering(true)
        //                .load();

        // Option 2 vertical scroll
        //        pdfView.fromFile(file)
        //                //.pages(0) // all pages are displayed by default
        //                .enableSwipe(true) // allows to block changing pages using swipe
        //                .swipeHorizontal(false)
        //                .enableDoubletap(true)
        //                .defaultPage(0)
        //                .onLoad(nbPages -> Log.d(TAG, "loadComplete " + nbPages)) // called after document is loaded and starts to be rendered
        //                .onPageChange((page, pageCount) -> {
        //                    Log.d(TAG, "onPageChange " + page + "/" + pageCount);
        //                })
        //                .onPageScroll((page, positionOffset) -> Log.d(TAG, "onPageScrolled " + page + " - " + positionOffset))
        //                .onError(t -> Log.e(TAG, "onError " + t.toString()))
        //                .onPageError((page, t) -> Log.e(TAG, "onPageError " + page + " -> " + t.toString()))
        //                .onRender(nbPages -> Log.d(TAG, "onInitiallyRendered nbPages " + nbPages)) // called after document is rendered for the first time
        //                // called on single tap, return true if handled, false to toggle scroll handle visibility
        //                .onTap(e -> {
        //                    Log.d(TAG, "onTap");
        //                    return false;
        //                })
        //                .onLongPress(e -> Log.d(TAG, "OnLongPressListener"))
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

        // Option 3 horizontal scroll
        pdfView.fromFile(file)
            // .pages(0) // all pages are displayed by default
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(true)
            .enableDoubletap(true)
            .defaultPage(0)
            .onLoad { nbPages ->
                logD("loadComplete $nbPages")
            } // called after document is loaded and starts to be rendered
            .onPageChange { page, pageCount ->
                logD("onPageChange $page/$pageCount")
            }
            .onPageScroll { page, positionOffset ->
                logD("onPageScrolled $page - $positionOffset")
            }
            .onError { t ->
                logE("onError $t")
            }
            .onPageError { page, t ->
                logE("onPageError $page -> $t")
            }
            .onRender { nbPages ->
                logD("onInitiallyRendered nbPages $nbPages")
            } // called after document is rendered for the first time
            // called on single tap, return true if handled, false to toggle scroll handle visibility
            .onTap {
                logD("onTap")
                false
            }
            .onLongPress {
                logD("OnLongPressListener")
            }
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
