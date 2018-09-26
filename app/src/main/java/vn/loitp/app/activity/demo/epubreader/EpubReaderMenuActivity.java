package vn.loitp.app.activity.demo.epubreader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LReaderUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.function.epub.Reader;
import vn.loitp.function.epub.exception.ReadingException;
import vn.loitp.function.epub.model.BookInfo;
import vn.loitp.views.LToast;

public class EpubReaderMenuActivity extends BaseFontActivity {
    private ProgressBar progressBar;
    private ListBookInfoTask listBookInfoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GridView) findViewById(R.id.grid_book_info)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookInfo bookInfo = (BookInfo) adapterView.getAdapter().getItem(i);
                LReaderUtil.readEpub(activity, bookInfo, getString(R.string.str_b));
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        listBookInfoTask = new ListBookInfoTask();
        listBookInfoTask.execute();
    }

    @Override
    protected void onDestroy() {
        if (listBookInfoTask != null) {
            listBookInfoTask.cancel(true);
        }
        super.onDestroy();
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
        return R.layout.activity_epub_reader_menu;
    }

    private class ListBookInfoTask extends AsyncTask<Object, Object, List<BookInfo>> {
        private Exception occuredException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
                    occuredException = e;
                    e.printStackTrace();
                }
            }
            return bookInfoList;
        }

        @Override
        protected void onPostExecute(List<BookInfo> bookInfoList) {
            super.onPostExecute(bookInfoList);
            progressBar.setVisibility(View.GONE);
            if (bookInfoList != null) {
                BookInfoGridAdapter adapter = new BookInfoGridAdapter(activity, bookInfoList);
                ((GridView) findViewById(R.id.grid_book_info)).setAdapter(adapter);
            }
            if (occuredException != null) {
                LToast.show(activity, occuredException.getMessage() + "");
            }
        }
    }

    private List<BookInfo> searchForFiles() {
        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        List<BookInfo> bookInfoList = null;
        if (isSDPresent) {
            bookInfoList = new ArrayList<>();
            List<File> files = LStoreUtil.getListEpubFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
            File sampleFile = LStoreUtil.getFileFromAssets(activity, "pg28885-images_new.epub");
            files.add(0, sampleFile);
            for (File file : files) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(file.getName());
                bookInfo.setFilePath(file.getPath());
                bookInfoList.add(bookInfo);
            }
        }
        return bookInfoList;
    }
}
