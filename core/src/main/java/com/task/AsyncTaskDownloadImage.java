package com.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.R;
import com.core.utilities.LSoundUtil;
import com.core.utilities.LStoreUtil;
import com.views.LToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//TODO convert corountine
public class AsyncTaskDownloadImage extends AsyncTask<String, Void, Bitmap> {
    private Context mContext;
    private String mURL;
    private String fileName;
    //private String description;
    private boolean downComplete = true;
    // ProgressDialog pb;

    private String sdCard;

    public AsyncTaskDownloadImage(@NonNull final Context c, @NonNull final String url) {
        this.mContext = c;
        this.mURL = url;
        //this.description = des;
        try {
            final String[] arr = url.split("/");
            fileName = arr[arr.length - 1];
        } catch (Exception e) {
            fileName = url;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... arg0) {
        downloadImagesToSdCard(mURL, fileName);
        return null;
    }

    private void downloadImagesToSdCard(final String downloadUrl, final String imageName) {
        try {
            final URL url = new URL(downloadUrl);
            sdCard = LStoreUtil.Companion.getFolderPath(mContext);
            final File myDir = new File(sdCard + "/Photo");

            /* if specified not exist create new */
            if (!myDir.exists()) {
                myDir.mkdir();
                //LLog.d(TAG, "inside mkdir");
            }

            /* checks the file and if it already exist delete */
            final File file = new File(myDir, imageName);
            if (file.exists())
                file.delete();

            /* Open a connection */
            final URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            final HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }

            final FileOutputStream fos = new FileOutputStream(file);
            final int totalSize = httpConn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

                // pb.setProgress((int) downloadedSize * 100 / totalSize);

                // LLog.d(TAG, "downloadedSize:" + downloadedSize +
                // "===totalSize:" + totalSize);
            }

            fos.close();
            // LLog.d(TAG, "Image Saved in sdcard..");
        } catch (Exception e) {
            downComplete = false;
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //LLog.d("onPostExecute", "onPostExecute downComplete: " + downComplete);
        if (downComplete) {
            LToast.show(mContext, "Download successful " + sdCard + fileName, R.drawable.l_bkg_horizontal);
            LSoundUtil.Companion.startMusicFromAsset(mContext, "ting.ogg");
        } else {
            LToast.show(mContext, R.string.download_failed, R.drawable.l_bkg_horizontal);
        }
        super.onPostExecute(result);
    }
}