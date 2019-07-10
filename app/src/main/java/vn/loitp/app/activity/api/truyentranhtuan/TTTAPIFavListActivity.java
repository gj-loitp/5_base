package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.GetFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

public class TTTAPIFavListActivity extends BaseFontActivity {
    private TextView tv;
    private TextView tvTitle;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        tvTitle = (TextView) findViewById(R.id.tv_title);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_api_ttt_fav_list;
    }

    private void getFavList() {
        new GetFavListTask(activity, avi, new GetFavListTask.Callback() {
            @Override
            public void onSuccess(List<Comic> comicList) {
                LLog.INSTANCE.d(TAG, "onSuccess " + comicList.size());
                LUIUtil.printBeautyJson(comicList, tv);
                tvTitle.setText("Danh sách yêu thích: " + comicList.size());
            }
        }).execute();
    }
}
