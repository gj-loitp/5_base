package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.GetFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class TTTAPIFavListActivity extends BaseActivity {
    private TextView tv;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();

        getFavList();
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
        return R.layout.activity_api_ttt_fav_list;
    }

    private void getFavList() {
        new GetFavListTask(activity, avi, new GetFavListTask.Callback() {
            @Override
            public void onSuccess(List<Comic> comicList) {
                LLog.d(TAG, "onSuccess " + comicList.size());
                LUIUtil.printBeautyJson(comicList, tv);
            }
        }).execute();
    }
}
