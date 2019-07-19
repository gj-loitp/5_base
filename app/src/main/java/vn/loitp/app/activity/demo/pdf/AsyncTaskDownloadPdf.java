package vn.loitp.app.activity.demo.pdf;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.core.utilities.LLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//TODO convert to rx
public class AsyncTaskDownloadPdf extends AsyncTask<String, Void, Boolean> {
    private final String TAG = getClass().getSimpleName();
    private String mURL;
    private String folderPath;
    private String fileName;
    private String folderName;

    public AsyncTaskDownloadPdf(@NonNull final String folderPath, @NonNull final String url, @NonNull final String folderName) {
        this.folderPath = folderPath;
        this.mURL = url;
        try {
            final String[] arr = url.split("/");
            fileName = arr[arr.length - 1];
        } catch (Exception e) {
            fileName = url;
        }
        this.folderName = folderName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        return downloadFileToSdCard(mURL, fileName);
    }

    private boolean downloadFileToSdCard(final String downloadUrl, final String imageName) {
        try {
            final URL url = new URL(downloadUrl);
            final File myDir = new File(folderPath + "/" + folderName);

            if (!myDir.exists()) {
                boolean isMkDirResult = myDir.mkdir();
                LLog.d(TAG, "isMkDirResult " + isMkDirResult);
            }

            /* checks the file and if it already exist delete */
            final File file = new File(myDir, imageName);
            if (file.exists()) {
                boolean isDeleted = file.delete();
                LLog.d(TAG, "isDeleted " + isDeleted);
            }

            /* Open a connection */
            final URLConnection urlConnection = url.openConnection();
            InputStream inputStream = null;
            final HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            if (inputStream == null) {
                return false;
            }
            final FileOutputStream fos = new FileOutputStream(file);
            final int totalSize = httpURLConnection.getContentLength();
            LLog.d(TAG, "totalSize " + totalSize);
            int downloadedSize = 0;
            final byte[] buffer = new byte[1024 * 2];
            int bufferLength;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                if (isCancelled()) {
                    return false;
                }
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                LLog.d(TAG, "downloadedSize: " + downloadedSize + ",totalSize: " + totalSize + " -> %: " + (downloadedSize * 100 / totalSize));
            }

            fos.close();
            LLog.d(TAG, "File saved in sdcard..");
            return true;
        } catch (IOException io) {
            LLog.e(TAG, "IOException" + io.toString());
            return false;
        } catch (Exception e) {
            LLog.e(TAG, "Exception" + e.toString());
            return false;
        }
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        LLog.d(TAG, "onCancelled aBoolean: " + aBoolean);
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onPostExecute(Boolean isDownloaded) {
        LLog.d("onPostExecute", "onPostExecute isDownloaded: " + isDownloaded);
        super.onPostExecute(isDownloaded);
    }
}