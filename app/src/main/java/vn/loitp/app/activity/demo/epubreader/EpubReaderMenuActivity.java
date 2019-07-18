package vn.loitp.app.activity.demo.epubreader;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.core.base.BaseFontActivity;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.core.utilities.LReaderUtil;
import com.core.utilities.LUIUtil;
import com.function.epub.model.BookInfo;
import com.function.epub.task.GetListBookAllAssetTask;
import com.function.epub.task.GetListBookFromDeviceAndAssetTask;

import java.util.List;

import loitp.basemaster.R;

public class EpubReaderMenuActivity extends BaseFontActivity {
    private ProgressBar progressBar;
    private GetListBookFromDeviceAndAssetTask getListBookFromDeviceAndAssetTask;
    private GetListBookAllAssetTask getListBookAllAssetTask;
    private final int MAX_BOOK_ASSET = 1;
    private final String EXTENSION_EPUB = ".sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GridView) findViewById(R.id.grid_book_info)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookInfo bookInfo = (BookInfo) adapterView.getAdapter().getItem(i);
                LReaderUtil.INSTANCE.readEpub(getActivity(), bookInfo, getString(R.string.str_b));
            }
        });
        progressBar = findViewById(R.id.progressbar);
        LUIUtil.INSTANCE.setDelay(1000, this::ask);
    }

    private void ask() {
        LDialogUtil.INSTANCE.showDialog2(getActivity(), "Chọn", "Có 2 option", "Load tất cả epub có trong device và 1 file ở asset", "Load tất cả epub trong asset", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                //lấy list epub ở trên all device và 1 file ở asset folder rồi show lên UI
                getListBookFromDeviceAndAssetTask = new GetListBookFromDeviceAndAssetTask(getActivity(), new GetListBookFromDeviceAndAssetTask.Callback() {
                    @Override
                    public void onPreExecute() {
                        LDialogUtil.INSTANCE.showProgress(progressBar);
                    }

                    @Override
                    public void onPostExecute(List<BookInfo> bookInfoList) {
                        LLog.d(getTAG(), "onPostExecute " + bookInfoList.size());
                        LDialogUtil.INSTANCE.hideProgress(progressBar);
                        BookInfoGridAdapter adapter = new BookInfoGridAdapter(getActivity(), bookInfoList);
                        ((GridView) findViewById(R.id.grid_book_info)).setAdapter(adapter);
                    }
                });
                getListBookFromDeviceAndAssetTask.execute();
            }

            @Override
            public void onClick2() {
                getListBookAllAssetTask = new GetListBookAllAssetTask(getApplicationContext(), MAX_BOOK_ASSET, EXTENSION_EPUB, new GetListBookAllAssetTask.Callback() {
                    @Override
                    public void onPreExecute() {
                        LDialogUtil.INSTANCE.showProgress(progressBar);
                    }

                    @Override
                    public void onPostExecute(List<BookInfo> bookInfoList) {
                        LLog.d(getTAG(), "onPostExecute " + bookInfoList.size());
                        LDialogUtil.INSTANCE.hideProgress(progressBar);
                        bookInfoList.addAll(bookInfoList);
                        /*bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);
                        bookInfoList.addAll(bookInfoList);*/
                        BookInfoGridAdapter adapter = new BookInfoGridAdapter(getActivity(), bookInfoList);
                        ((GridView) findViewById(R.id.grid_book_info)).setAdapter(adapter);
                    }
                });
                getListBookAllAssetTask.execute();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (getListBookFromDeviceAndAssetTask != null) {
            getListBookFromDeviceAndAssetTask.cancel(true);
        }
        if (getListBookAllAssetTask != null) {
            getListBookAllAssetTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_epub_reader_menu;
    }
}
