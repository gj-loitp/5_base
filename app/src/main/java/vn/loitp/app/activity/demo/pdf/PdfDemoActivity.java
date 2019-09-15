package vn.loitp.app.activity.demo.pdf;

import android.os.AsyncTask;
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
import com.task.AsyncTaskDownloadPdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import loitp.basemaster.R;

//https://github.com/barteksc/AndroidPdfViewer
public class PdfDemoActivity extends BaseFontActivity {
    private AsyncTaskDownloadPdf asyncTaskDownloadPdf;
    private PDFView pdfView;
    private ProgressBar pb;
    private Button btFile;
    private Button btStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfView = findViewById(R.id.pdfView);
        pb = findViewById(R.id.pb);
        btFile = findViewById(R.id.btFile);
        btStream = findViewById(R.id.btStream);
        btFile.setOnClickListener(view -> {
            fromUrl();
        });
        btStream.setOnClickListener(view -> {
            new RetrievePDFStream().execute("http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf");
            //new RetrievePDFStream().execute("http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf");
        });

        showDialogMsg("You can load pdf from url, uri, file, asset, bytes, stream...");
    }


    private class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            LLog.d(TAG, "doInBackground");
            InputStream inputStream = null;
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                LLog.e(TAG, "doInBackground MalformedURLException " + e.toString());
            }

            try {
                HttpURLConnection httpURLConnection = null;
                if (url != null) {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                }
                if (httpURLConnection != null && httpURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }
            } catch (IOException e) {
                LLog.d(TAG, "doInBackground IOException " + e.toString());
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            LLog.d(TAG, "onPostExecute");

            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromStream(inputStream).load();
        }
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
        //final String url = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
        //final String url = "http://www.pdf995.com/samples/pdf.pdf";
        //final String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        final String url = "http://ftp.geogratis.gc.ca/pub/nrcan_rncan/publications/ess_sst/222/222861/mr_93_e.pdf";
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
                btFile.setVisibility(View.GONE);
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
//                .nightMode(false) // toggle night parrallaxMode
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
                .nightMode(false) // toggle night parrallaxMode
                .load();
    }

    @Override
    public void onBackPressed() {
        if (pdfView.getVisibility() == View.VISIBLE) {
            pdfView.setVisibility(View.GONE);
            btFile.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}