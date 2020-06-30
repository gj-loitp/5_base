package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.wang.avi.AVLoadingIndicatorView;

import vn.loitp.app.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.GetFavListTask;

public class TTTAPIFavListActivity extends BaseFontActivity {
    private TextView tv;
    private TextView tvTitle;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.textView);
        tvTitle = findViewById(R.id.tvTitle);
        avLoadingIndicatorView = findViewById(R.id.indicatorView);
        avLoadingIndicatorView.hide();

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
        new GetFavListTask(getActivity(), avLoadingIndicatorView, comicList -> {
            LLog.d(getTAG(), "onSuccess " + comicList.size());
            LUIUtil.Companion.printBeautyJson(comicList, tv);
            tvTitle.setText("Danh sách yêu thích: " + comicList.size());
        }).execute();
    }
}
