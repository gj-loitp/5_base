package vn.loitp.app.activity.demo.pdf;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;

import loitp.basemaster.R;

public class PdfDemoActivity extends BaseFontActivity {
    private AsyncTaskDownloadPdf asyncTaskDownloadPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btDownload).setOnClickListener(view -> {
            fromUrl();
        });
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

    private void fromUrl(){
        if (asyncTaskDownloadPdf != null) {
            asyncTaskDownloadPdf.cancel(true);
        }
        final String folderPath = LStoreUtil.getFolderPath(activity, "ZZZDemoPDF");
        final String url = "http://www.peoplelikeus.org/piccies/codpaste/codpaste-teachingpack.pdf";
        final String folderName = "PDFDemo";
        asyncTaskDownloadPdf = new AsyncTaskDownloadPdf(folderPath, url, folderName, new AsyncTaskDownloadPdf.Callback() {
            @Override
            public void onSuccess(long durationSec, String durationHHmmss) {
                LLog.d(TAG, "onSuccess " + durationSec + " - " + durationHHmmss);
            }

            @Override
            public void onError(Exception e) {
                LLog.d(TAG, "onError");
            }

            @Override
            public void onProgressUpdate(int downloadedSize, int totalSize, float percent) {
                LLog.d(TAG, "onProgressUpdate " + downloadedSize + " - " + totalSize + " - " + percent);
            }
        });
        asyncTaskDownloadPdf.execute();
    }
}
