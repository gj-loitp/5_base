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
    private int maxBookAsset;
    private String extensionEpub;
    private List<BookInfo> bookInfoList = new ArrayList<>();
    private Context context;
    private Callback callback;

    public interface Callback {
        public void onPreExecute();

        public void onPostExecute(List<BookInfo> bookInfoList);
    }

    public GetListBookAllAssetTask(Context context, int maxBookAsset, String extensionEpub, Callback callback) {
        this.context = context;
        this.maxBookAsset = maxBookAsset;
        this.extensionEpub = extensionEpub;
        this.callback = callback;
        LLog.d(TAG, "GetListBookAllAssetTask maxBookAsset " + maxBookAsset + ", extensionEpub: " + extensionEpub);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>onPreExecute asset");
        if (callback != null) {
            callback.onPreExecute();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>doInBackground");
        String jsonBookAsset = LPref.getJsonBookAsset(context);
        if (jsonBookAsset == null || jsonBookAsset.isEmpty()) {
            bookInfoList.addAll(searchForFiles());
        } else {
            List<BookInfo> tmpList = new Gson().fromJson(jsonBookAsset, new TypeToken<List<BookInfo>>() {
            }.getType());
            if (tmpList != null) {
                bookInfoList.addAll(tmpList);
            } else {
                bookInfoList.addAll(searchForFiles());
            }
        }
        LLog.d(TAG, "doInBackground searchForPdfFiles " + bookInfoList.size());
        Reader reader = new Reader();
        for (int i = 0; i < bookInfoList.size(); i++) {
            try {
                BookInfo bookInfo = bookInfoList.get(i);
                if (bookInfo == null || bookInfo.getFilePath() == null) {
                    continue;
                }
                reader.setInfoContent(bookInfo.getFilePath());
                String title = reader.getInfoPackage().getMetadata().getTitle();
                if (title != null && !title.equals("")) {
                    bookInfo.setTitle(title);
                } else {
                    // If txtTitle doesn't exist, use fileName instead.
                    int dotIndex = bookInfo.getTitle().lastIndexOf('.');
                    bookInfo.setTitle(bookInfo.getTitle().substring(0, dotIndex));
                }
                bookInfo.setCoverImage(reader.getCoverImage());
            } catch (ReadingException e) {
                LLog.e(TAG, "doInBackground ReadingException " + e.toString());
                e.printStackTrace();
            }
        }
        sortABC();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LLog.d(TAG, ">>>>>>>>>>>>>>>>>>onPostExecute asset " + bookInfoList.size());
        if (callback != null) {
            callback.onPostExecute(bookInfoList);
        }
        super.onPostExecute(aVoid);
    }

    private void sortABC() {
        if (bookInfoList == null || bookInfoList.isEmpty()) {
            LLog.e(TAG, "sortABC bookInfoList==null||isEmpty return");
        }
        Collections.sort(bookInfoList, new Comparator<BookInfo>() {
            @Override
            public int compare(BookInfo a1, BookInfo a2) {
                return a1.getTitle().compareToIgnoreCase(a2.getTitle());
            }
        });

    }

    private List<BookInfo> searchForFiles() {
        List<File> files = new ArrayList<>();
        List<BookInfo> infoList = new ArrayList<>();
        File f;
        for (int i = 1; i <= maxBookAsset; i++) {
            LLog.d(TAG, "searchForFiles " + i);
            f = getFileFromAssets("a (" + i + ")" + extensionEpub);
            if (f != null) {
                files.add(f);
            }
        }
        for (File file : files) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setTitle(file.getName());
            bookInfo.setFilePath(file.getPath());
            infoList.add(bookInfo);
        }
        return infoList;
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
        LLog.d(TAG, "getFileFromAssets fileName " + fileName);
        return file;
    }
}