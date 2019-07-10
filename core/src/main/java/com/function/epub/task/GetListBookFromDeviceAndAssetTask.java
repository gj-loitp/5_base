package com.function.epub.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;

import com.core.utilities.LStoreUtil;
import com.function.epub.Reader;
import com.function.epub.exception.ReadingException;
import com.function.epub.model.BookInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetListBookFromDeviceAndAssetTask extends AsyncTask<Object, Object, List<BookInfo>> {
    private Callback callback;
    private Activity activity;

    public interface Callback {
        public void onPreExecute();

        public void onPostExecute(List<BookInfo> bookInfoList);
    }

    public GetListBookFromDeviceAndAssetTask(Activity activity, Callback callback) {
        this.activity = activity;
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
    protected List<BookInfo> doInBackground(Object... params) {
        List<BookInfo> bookInfoList = searchForFiles();
        Reader reader = new Reader();
        for (BookInfo bookInfo : bookInfoList) {
            try {
                reader.setInfoContent(bookInfo.getFilePath());
                String title = reader.getInfoPackage().getMetadata().getTitle();
                if (title != null && !title.equals("")) {
                    bookInfo.setTitle(reader.getInfoPackage().getMetadata().getTitle());
                } else {
                    // If title doesn't exist, use fileName instead.
                    int dotIndex = bookInfo.getTitle().lastIndexOf('.');
                    bookInfo.setTitle(bookInfo.getTitle().substring(0, dotIndex));
                }
                bookInfo.setCoverImage(reader.getCoverImage());
            } catch (ReadingException e) {
                e.printStackTrace();
            }
        }
        return bookInfoList;
    }

    @Override
    protected void onPostExecute(List<BookInfo> bookInfoList) {
        super.onPostExecute(bookInfoList);
        if (callback != null) {
            callback.onPostExecute(bookInfoList);
        }
    }

    private List<BookInfo> searchForFiles() {
        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        List<BookInfo> bookInfoList = null;
        if (isSDPresent) {
            bookInfoList = new ArrayList<>();
            List<File> files = LStoreUtil.getListEpubFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
            File sampleFile = LStoreUtil.getFileFromAssets(activity, "a (1).sqlite");
            files.add(0, sampleFile);
            for (File file : files) {
                if (file == null) {
                    continue;
                }
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(file.getName() + "");
                bookInfo.setFilePath(file.getPath() + "");
                bookInfoList.add(bookInfo);
            }
        }
        return bookInfoList;
    }
}
