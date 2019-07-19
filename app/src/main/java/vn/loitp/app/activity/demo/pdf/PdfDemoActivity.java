package vn.loitp.app.activity.demo.pdf;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import loitp.basemaster.R;

//https://github.com/barteksc/AndroidPdfViewer
public class PdfDemoActivity extends BaseFontActivity {
    private AsyncTaskDownloadPdf asyncTaskDownloadPdf;
    private PDFView pdfView;
    private ProgressBar pb;
    private Button btDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfView = findViewById(R.id.pdfView);
        pb = findViewById(R.id.pb);
        btDownload = findViewById(R.id.btDownload);
        btDownload.setOnClickListener(view -> {
            fromUrl();
        });
        showDialogMsg("You can load pdf from url, uri, file, asset, bytes, stream...");
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_pdf_demo;
    }

    @Override
    protected void onDestroy() {
        if (asyncTaskDownloadPdf != null) {
            asyncTaskDownloadPdf.cancel(true);
        }
        super.onDestroy();
    }

    private void fromUrl() {
        if (asyncTaskDownloadPdf != null) {
            asyncTaskDownloadPdf.cancel(true);
        }
        final String folderPath = LStoreUtil.getFolderPath(activity, "ZZZDemoPDF");
        final String url = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
        final String folderName = "PDFDemo";
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);
        pdfView.setVisibility(View.GONE);
        asyncTaskDownloadPdf = new AsyncTaskDownloadPdf(folderPath, url, folderName, new AsyncTaskDownloadPdf.Callback() {

            @Override
            public void onSuccess(long durationSec, String durationHHmmss, File file) {
                LLog.d(TAG, "onSuccess " + durationSec + " - " + durationHHmmss);
                LLog.d(TAG, "onSuccess " + file.getPath());
                showShort("onSuccess after " + durationSec + " seconds");
                pb.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                btDownload.setVisibility(View.GONE);
                showPDF(file);
            }

            @Override
            public void onError(Exception e) {
                LLog.d(TAG, "onError");
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onProgressUpdate(int downloadedSize, int totalSize, float percent) {
                LLog.d(TAG, "onProgressUpdate " + downloadedSize + " - " + totalSize + " - " + percent);
                pb.setProgress((int) percent);
            }
        });
        asyncTaskDownloadPdf.execute();
    }

    private void showPDF(@NonNull File file) {
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
//                .nightMode(false) // toggle night mode
//                .load();


        //Option 3 horizontal scroll
        pdfView.fromFile(file)
                //.pages(0) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .onLoad(nbPages -> LLog.d(TAG, "loadComplete " + nbPages)) // called after document is loaded and starts to be rendered
                .onPageChange((page, pageCount) -> {
                    LLog.d(TAG, "onPageChange " + page + "/" + pageCount);
                })
                .onPageScroll((page, positionOffset) -> LLog.d(TAG, "onPageScrolled " + page + " - " + positionOffset))
                .onError(t -> LLog.e(TAG, "onError " + t.toString()))
                .onPageError((page, t) -> LLog.e(TAG, "onPageError " + page + " -> " + t.toString()))
                .onRender(nbPages -> LLog.d(TAG, "onInitiallyRendered nbPages " + nbPages)) // called after document is rendered for the first time
                // called on single tap, return true if handled, false to toggle scroll handle visibility
                .onTap(e -> {
                    LLog.d(TAG, "onTap");
                    return false;
                })
                .onLongPress(e -> LLog.d(TAG, "OnLongPressListener"))
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(new DefaultScrollHandle(this))
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .load();
    }

    @Override
    public void onBackPressed() {
        if (pdfView.getVisibility() == View.VISIBLE) {
            pdfView.setVisibility(View.GONE);
            btDownload.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
