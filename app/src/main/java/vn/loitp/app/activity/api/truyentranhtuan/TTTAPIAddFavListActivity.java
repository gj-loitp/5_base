package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.AddComicFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class TTTAPIAddFavListActivity extends BaseActivity {
    private TextView tv;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();

        findViewById(R.id.bt_add_one_piece).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = new Comic();
                addComic(comic);
            }
        });
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_api_ttt_add_fav_list;
    }

    private void addComic(Comic comic) {
        new AddComicFavListTask(activity, comic, new AddComicFavListTask.Callback() {
            @Override
            public void onAddComicSuccess(Comic mComic, List<Comic> comicList) {
                LUIUtil.printBeautyJson(comicList, tv);
                ToastUtils.showShort("onAddComicSuccess");
            }

            @Override
            public void onComicIsExist() {
                ToastUtils.showShort("onComicIsExist");
                tv.setText(comic.getTitle() + " is exist");
            }

            @Override
            public void onAddComicError() {
                ToastUtils.showShort("onAddComicError");
                tv.setText("add error");
            }
        });
    }
}
