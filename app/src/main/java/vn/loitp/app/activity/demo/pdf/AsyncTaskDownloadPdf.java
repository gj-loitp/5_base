package vn.loitp.app.activity.demo.pdf;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.utilities.LDateUtils;
import com.core.utilities.LLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//TODO convert to rx
public class AsyncTaskDownloadPdf extends AsyncTask<String, Integer, Boolean> {
    private final String TAG = getClass().getSimpleName();
    private String mURL;
    private String folderPath;
    private String fileName;
    private String folderName;
    private long startTime;
    private Callback callback;
    private Exception exception;

    public AsyncTaskDownloadPdf(@NonNull final String folderPath, @NonNull final String url, @NonNull final String folderName, @Nullable Callback callback) {
        this.folderPath = folderPath;
        this.mURL = url;
        try {
            final String[] arr = url.split("/");
            fileName = arr[arr.length - 1];
        } catch (Exception e) {
            fileName = url;
        }
        this.folderName = folderName;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        startTime = System.currentTimeMillis();
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
                    LLog.d(TAG, "isCancelled -> return false");
                    return false;
                }
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                publishProgress(downloadedSize, totalSize);
            }

            fos.close();
            LLog.d(TAG, "File saved in sdcard..");
            return true;
        } catch (IOException ioException) {
            exception = ioException;
            LLog.e(TAG, "IOException" + ioException.toString());
            return false;
        } catch (Exception e) {
            exception = e;
            LLog.e(TAG, "Exception" + e.toString());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        final int downloadedSize = values[0];
        final int totalSize = values[1];
        final float percent = (float) downloadedSize * 100 / (float) totalSize;
        //LLog.d(TAG, "onProgressUpdate: " + downloadedSize + "-" + totalSize + " -> " + ((float) downloadedSize * 100 / (float) totalSize) + "%");
        if (callback != null) {
            callback.onProgressUpdate(downloadedSize, totalSize, percent);
        }
    }

    @Override
    protected void onPostExecute(Boolean isDownloaded) {
        LLog.d(TAG, "onPostExecute isDownloaded: " + isDownloaded);
        if (isDownloaded) {
            if (callback != null) {
                final long endTime = System.currentTimeMillis();
                final long durationSec = (endTime - startTime) / 1000;
                final String duration = LDateUtils.INSTANCE.convertSToFormat(durationSec, "HH:mm:ss");
                LLog.d(TAG, "onPostExecute duration: " + duration);
                callback.onSuccess(durationSec, duration);
            }
        } else {
            if (callback != null) {
                callback.onError(exception);
            }
        }
        super.onPostExecute(isDownloaded);
    }

    public interface Callback {
        void onSuccess(long durationSec, String durationHHmmss);

        void onError(Exception e);

        void onProgressUpdate(int downloadedSize, int totalSize, float percent);
    }
}