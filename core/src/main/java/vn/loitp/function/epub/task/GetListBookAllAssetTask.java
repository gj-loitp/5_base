package vn.loitp.function.epub.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.function.epub.Reader;
import vn.loitp.function.epub.exception.ReadingException;
import vn.loitp.function.epub.model.BookInfo;

public class GetListBookAllAssetTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    private boolean isSuccess = true;
    private int maxBookAsset;
    private String extensionEpub;
    private List<BookInfo> bookInfoList = new ArrayList<>();
    private Context context;
    private Callback callback;

    public interface Callback {
        public void onPreExecute();

        public void onPostExecute(List<BookInfo> bookInfoList);

        public void onError();
    }

    public GetListBookAllAssetTask(Context context, int maxBookAsset, String extensionEpub, Callback callback) {
        this.context = context;
        this.maxBookAsset = maxBookAsset;
        this.extensionEpub = extensionEpub;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>onPreExecute asset");
        super.onPreExecute();
        if (callback != null) {
            callback.onPreExecute();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>doInBackground");
        String jsonBookAsset = LPref.getJsonBookAsset(context);
        if (jsonBookAsset == null || jsonBookAsset.isEmpty()) {
            searchForFiles();
        } else {
            List<BookInfo> tmpList = new Gson().fromJson(jsonBookAsset, new TypeToken<List<BookInfo>>() {
            }.getType());
            if (tmpList != null) {
                bookInfoList.addAll(tmpList);
            } else {
                searchForFiles();
            }
        }
        LLog.d(TAG, "doInBackground searchForPdfFiles " + bookInfoList.size());
        sortABC();
        Reader reader = new Reader();
        for (BookInfo bookInfo : bookInfoList) {
            try {
                reader.setInfoContent(bookInfo.getFilePath());
                String title = reader.getInfoPackage().getMetadata().getTitle();
                if (title != null && !title.equals("")) {
                    bookInfo.setTitle(reader.getInfoPackage().getMetadata().getTitle());
                } else {
                    // If txtTitle doesn't exist, use fileName instead.
                    int dotIndex = bookInfo.getTitle().lastIndexOf('.');
                    bookInfo.setTitle(bookInfo.getTitle().substring(0, dotIndex));
                }
                bookInfo.setCoverImage(reader.getCoverImage());
            } catch (ReadingException e) {
                isSuccess = false;
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>onPostExecute asset");
        if (isSuccess) {
            if (callback != null) {
                callback.onPostExecute(bookInfoList);
            }
        } else {
            if (callback != null) {
                callback.onError();
            }
        }
        super.onPostExecute(aVoid);
    }

    private void sortABC() {
        Collections.sort(bookInfoList, new Comparator<BookInfo>() {
            @Override
            public int compare(BookInfo a1, BookInfo a2) {
                return a1.getTitle().compareToIgnoreCase(a2.getTitle());
            }
        });
    }

    private List<BookInfo> searchForFiles() {
        List<File> files = new ArrayList<>();
        File f;
        for (int i = 1; i <= maxBookAsset; i++) {
            f = getFileFromAssets("a (" + i + ")" + extensionEpub);
            if (f != null) {
                files.add(f);
            }
        }
        for (File file : files) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setTitle(file.getName());
            bookInfo.setFilePath(file.getPath());
            bookInfoList.add(bookInfo);
        }
        return bookInfoList;
    }

    private File getFileFromAssets(String fileName) {
        File file = new File(context.getCacheDir() + "/" + fileName);
        if (!file.exists()) {
            try {
                InputStream is = context.getAssets().open("b/" + fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buffer);
                fos.close();
            } catch (Exception e) {
                LLog.e(TAG, "getFileFromAssets " + e.toString());
                return null;
            }
        }
        return file;
    }
}