package vn.loitp.task;

/**
 * Created by www.muathu@gmail.com on 12/31/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import loitp.core.R;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.LToast;

/**
 * Created by HOME on 12/27/2016.
 */

public class AsyncTaskDownloadImage extends AsyncTask<String, Void, Bitmap> {
    private Context mContext;
    private String mURL;
    private String fileName;
    //private String description;
    private boolean downComplete = true;
    // ProgressDialog pb;

    private String sdCard;

    public AsyncTaskDownloadImage(Context c, String url) {
        this.mContext = c;
        this.mURL = url;
        //this.description = des;
        try {
            String[] arr = url.split("/");
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

    private void downloadImagesToSdCard(String downloadUrl, String imageName) {
        try {
            URL url = new URL(downloadUrl);
            sdCard = LStoreUtil.getFolderPath(mContext);
            File myDir = new File(sdCard + "/Photo");

            /* if specified not exist create new */
            if (!myDir.exists()) {
                myDir.mkdir();
                //LLog.d(TAG, "inside mkdir");
            }

            /* checks the file and if it already exist delete */
            String fname = imageName;
            File file = new File(myDir, fname);
            if (file.exists())
                file.delete();

            /* Open a connection */
            URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = httpConn.getContentLength();
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
        } catch (IOException io) {
            downComplete = false;

            io.printStackTrace();
            // LLog.d(TAG, "IOException" + io.toString());
        } catch (Exception e) {
            downComplete = false;
            // LLog.d(TAG, "Exception" + e.toString());
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (downComplete) {
            LToast.show(mContext, "Download successful " + sdCard + fileName);
        } else {
            LToast.show(mContext, R.string.download_failed);
        }
        super.onPostExecute(result);
    }
}