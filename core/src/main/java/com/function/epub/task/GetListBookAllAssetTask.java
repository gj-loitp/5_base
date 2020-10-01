package com.function.epub.task;

import android.content.Context;
import android.os.AsyncTask;

import com.core.base.BaseApplication;
import com.core.utilities.LPrefUtil;
import com.function.epub.Reader;
import com.function.epub.exception.ReadingException;
import com.function.epub.model.BookInfo;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//TODO convert to coroutine
public class GetListBookAllAssetTask extends AsyncTask<Void, Void, Void> {
    private final String logTag = getClass().getSimpleName();
    private int maxBookAsset;
    private String extensionEpub;
    private List<BookInfo> bookInfoList = new ArrayList<>();
    private Context context;
    private Callback callback;

    public interface Callback {
        void onPreExecute();

        void onPostExecute(List<BookInfo> bookInfoList);
    }

    public GetListBookAllAssetTask(Context context, int maxBookAsset, String extensionEpub, Callback callback) {
        this.context = context;
        this.maxBookAsset = maxBookAsset;
        this.extensionEpub = extensionEpub;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback != null) {
            callback.onPreExecute();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        String jsonBookAsset = LPrefUtil.Companion.getJsonBookAsset();
        if (jsonBookAsset == null || jsonBookAsset.isEmpty()) {
            bookInfoList.addAll(searchForFiles());
        } else {
            List<BookInfo> tmpList = BaseApplication.Companion.getGson().fromJson(jsonBookAsset, new TypeToken<List<BookInfo>>() {
            }.getType());
            if (tmpList != null) {
                bookInfoList.addAll(tmpList);
            } else {
                bookInfoList.addAll(searchForFiles());
            }
        }
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
                e.printStackTrace();
            }
        }
        sortABC();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            callback.onPostExecute(bookInfoList);
        }
        super.onPostExecute(aVoid);
    }

    private void sortABC() {
        if (bookInfoList == null || bookInfoList.isEmpty()) {
            return;
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
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
