package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.progressloadingview.avl.LAVLoadingIndicatorView;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist.GetChapTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap;

public class TTTAPIChapListActivity extends BaseFontActivity {
    private TextView tvTitle;
    private TextView tv;
    private LAVLoadingIndicatorView avi;
    private Button btSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btSelect = (Button) findViewById(R.id.bt_select);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv = (TextView) findViewById(R.id.tv);
        avi = (LAVLoadingIndicatorView) findViewById(R.id.avi);

        String urlComic = "http://truyentranhtuan.com/one-piece/";
        new GetChapTask(getActivity(), urlComic, new GetChapTask.Callback() {
            @Override
            public void onSuccess(TTTChap tttChap) {
                LUIUtil.INSTANCE.printBeautyJson(tttChap, tv);
                avi.smoothToHide();
                tvTitle.setText("Chap truyện One Piece - size: " + tttChap.getChaps().getChap().size());
            }

            @Override
            public void onError() {
                showShort("onError");
            }
        }).execute();
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
        return R.layout.activity_api_ttt_chap_list;
    }
}
